package net.wss.rs.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.sun.faces.util.Util;

import net.wss.rs.jdbc.ReadDBTable;
import net.wss.rs.util.FileUtil;
import net.wss.rs.util.StringUtil;

public class DataSourceConv {
	
	/**
	 * 读alldata数据集处理疾病的名字及相对应的诊次
	 */
	public void readAllDataTxtFile(String filePath) {
		ReadDBTable rdtable=new ReadDBTable();
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
						FileUtil.appendData(DataSetConfig.AllDoctorPath, docInfo[0]+DataSetConfig.AttrSplit+docInfo[1]);						
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
					 
					
					String[] diseaseArray = disInfo.split(",");// 将读取得字符串分割，放入一个数组中
//					System.out.println("line text:" + docInfo[2]);
					for (int i = 0; i < diseaseArray.length; i++) {
						String nameAndRating = diseaseArray[i].trim();					
						String[] disNameRatingArray = nameAndRating.split(":");
						String disName ="";
						String disId ="";
						String rating = "";
						// System.out.println(disName[0]);
						if (disNameRatingArray.length == 2 && disNameRatingArray[0].trim() != "") {
							disName = disNameRatingArray[0];
							disId = getDiseaseId(disName);//调用方法获得疾病的id
							rating= disNameRatingArray[1].trim();
							//将医生id,疾病id,相对应疾病的诊次，写入ratingall.txt文件中
							FileUtil.appendData(DataSetConfig.AllRatingPath,docInfo[0]+ DataSetConfig.AttrSplit+disId+DataSetConfig.AttrSplit+rating);
//							System.out.print(disName[0] + " " + disName[1]);
				/**
				 * 当处理后的alldata.txt文件中，数据格式有错误的时候，使用下面的这些部分去处理
				 * 如果处理后的数据集不存在问题，利用上面的if即可写入相关的文件		
				 */
						} else if(disNameRatingArray.length==1){
//							System.out.print("error: ");
							int len = disNameRatingArray[0].length();
							String numStr = disNameRatingArray[0].substring(len - 2, len);
							if (StringUtil.isNumeric(numStr)) {
//								System.out.print(disNameRatingArray[0].substring(0,len - 2) + " " + numStr);								
								disName = disNameRatingArray[0].substring(0,len - 2);
								disId = getDiseaseId(disName);
								rating=numStr.trim();
							}
							numStr = disNameRatingArray[0].substring(len - 1, len);
							if (StringUtil.isNumeric(numStr)) {
//								System.out.print(disNameRatingArray[0].substring(0,len - 1) + " " + numStr);
								
								disName = disNameRatingArray[0].substring(0,len - 1);
								disId = getDiseaseId(disName);
								rating=numStr.trim();
							}
							//将医生id,疾病id,相对应疾病的诊次，写入ratingall.txt文件中
							FileUtil.appendData(DataSetConfig.AllRatingPath,docInfo[0]+ DataSetConfig.AttrSplit+disId+DataSetConfig.AttrSplit+rating);
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
	 * 获取疾病的id,
	 * 边判断重复，边写入，然后给其赋一个id
	 * 首先先判断diseaseall.txt文件中是否有数据
	 * 
	 * @param disName
	 * @return
	 */
	public static  String getDiseaseId(String disName){
		File file = new File(DataSetConfig.AllDiseasePath);
			
		String encoding = "GBK";
		String  maxId = "0";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = "";
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String[] lineArray = lineTxt.split(DataSetConfig.AttrSplit);
					if(lineArray[1].equals(disName.trim())){
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
		//将获得的疾病写入到diseaseall.txt文件中		
		FileUtil.appendData(DataSetConfig.AllDiseasePath,newid+DataSetConfig.AttrSplit+disName.trim());
		
		return String.valueOf(newid);
		
	}
}
