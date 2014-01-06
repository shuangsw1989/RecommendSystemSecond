package net.wss.rs.data.zheng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import net.wss.rs.jdbc.zheng.ZhengReadDBTable;
import net.wss.rs.util.FileUtil;
import net.wss.rs.util.StringUtil;

public class ZhengDataSourceConv {
	/**
	 * 读alldata数据集处理症状的名字及相对应的诊次
	 */
	public void readAllDataTxtFile(String filePath) {
		ZhengReadDBTable rdtable=new ZhengReadDBTable();
		File file = new File(filePath);

		if (file.isFile() && file.exists()) {
			String encoding = "GBK";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					if (lineTxt.length() == 0) {
						continue;
					}
					String[] docInfo = lineTxt.split(";");
					
					if(docInfo.length>=2){
						//将获得数据的前两列写入到dotorall.txt中
						FileUtil.appendData(ZhengDataSetConfig.AllDoctorPath, docInfo[0]+ZhengDataSetConfig.AttrSplit+docInfo[1]);						
					}else{
						continue;
					}
					
					String disInfo = "";
					if(docInfo.length ==3 ){
						disInfo = docInfo[2];
					}else{
						continue;
					}
					if(disInfo.equals("") || disInfo.equals("null")){
						continue;
					}
					 
					
					String[] zhengArray = disInfo.split(",");// 将读取得字符串分割，放入一个数组中
//					System.out.println("line text:" + docInfo[2]);
					for (int i = 0; i < zhengArray.length; i++) {
						String nameAndRating = zhengArray[i].trim();					
						String[] zhengNameRatingArray = nameAndRating.split(":");
						String zhengName ="";
						String zhengId ="";
						String rating = "";
						// System.out.println(zhengName[0]);
						if (zhengNameRatingArray.length == 2 && zhengNameRatingArray[0].trim() != "") {
							zhengName = zhengNameRatingArray[0];
							zhengId = getZhengId(zhengName);//调用方法获得症状的id
							rating= zhengNameRatingArray[1].trim();
							//将医生id,症状id,相对应症状的诊次，写入ratingall.txt文件中
							FileUtil.appendData(ZhengDataSetConfig.AllRatingPath,docInfo[0]+ ZhengDataSetConfig.AttrSplit+zhengId+ZhengDataSetConfig.AttrSplit+rating);
//							System.out.print(zhengName[0] + " " + zhengName[1]);
				/**
				 * 当处理后的alldata.txt文件中，数据格式有错误的时候，使用下面的这些部分去处理
				 * 如果处理后的数据集不存在问题，利用上面的if即可写入相关的文件		
				 */
						} else if(zhengNameRatingArray.length==1){
//							System.out.print("error: ");
							int len = zhengNameRatingArray[0].length();
							String numStr = zhengNameRatingArray[0].substring(len - 2, len);
							if (StringUtil.isNumeric(numStr)) {
//								System.out.print(zhengNameRatingArray[0].substring(0,len - 2) + " " + numStr);								
								zhengName = zhengNameRatingArray[0].substring(0,len - 2);
								zhengId = getZhengId(zhengName);
								rating=numStr.trim();
							}
							numStr = zhengNameRatingArray[0].substring(len - 1, len);
							if (StringUtil.isNumeric(numStr)) {
//								System.out.print(zhengNameRatingArray[0].substring(0,len - 1) + " " + numStr);
								
								zhengName = zhengNameRatingArray[0].substring(0,len - 1);
								zhengId = getZhengId(zhengName);
								rating=numStr.trim();
							}
							//将医生id,症状id,相对应症状的诊次，写入ratingall.txt文件中
							FileUtil.appendData(ZhengDataSetConfig.AllRatingPath,docInfo[0]+ ZhengDataSetConfig.AttrSplit+zhengId+ZhengDataSetConfig.AttrSplit+rating);
						}
						
						
						
//						System.out.println();
					}
				}

				read.close();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println(file.getName() + " not exist");
		}
	}
	
	/**
	 * 获取症状的id,
	 * 边判断重复，边写入，然后给其赋一个id
	 * 首先先判断diseaseall.txt文件中是否有数据
	 * 
	 * @param zhengName
	 * @return
	 */
	public static  String getZhengId(String zhengName){
		File file = new File(ZhengDataSetConfig.AllZhengPath);
			
		String encoding = "GBK";
		String  maxId = "0";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = "";
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String[] lineArray = lineTxt.split(ZhengDataSetConfig.AttrSplit);
					if(lineArray[1].equals(zhengName.trim())){
						return lineArray[0];
					}
					maxId = lineArray[0];
					
				}
				bufferedReader.close();
				read.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		
		int newid = Integer.valueOf(maxId)+1;
		//将获得的症状写入到diseaseall.txt文件中		
		FileUtil.appendData(ZhengDataSetConfig.AllZhengPath,newid+ZhengDataSetConfig.AttrSplit+zhengName.trim());
		
		return String.valueOf(newid);
}
}
