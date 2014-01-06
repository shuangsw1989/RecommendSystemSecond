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
	 * ��alldata���ݼ�����֢״�����ּ����Ӧ�����
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
						//��������ݵ�ǰ����д�뵽dotorall.txt��
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
					 
					
					String[] zhengArray = disInfo.split(",");// ����ȡ���ַ����ָ����һ��������
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
							zhengId = getZhengId(zhengName);//���÷������֢״��id
							rating= zhengNameRatingArray[1].trim();
							//��ҽ��id,֢״id,���Ӧ֢״����Σ�д��ratingall.txt�ļ���
							FileUtil.appendData(ZhengDataSetConfig.AllRatingPath,docInfo[0]+ ZhengDataSetConfig.AttrSplit+zhengId+ZhengDataSetConfig.AttrSplit+rating);
//							System.out.print(zhengName[0] + " " + zhengName[1]);
				/**
				 * ��������alldata.txt�ļ��У����ݸ�ʽ�д����ʱ��ʹ���������Щ����ȥ����
				 * ������������ݼ����������⣬���������if����д����ص��ļ�		
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
							//��ҽ��id,֢״id,���Ӧ֢״����Σ�д��ratingall.txt�ļ���
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
	 * ��ȡ֢״��id,
	 * ���ж��ظ�����д�룬Ȼ����丳һ��id
	 * �������ж�diseaseall.txt�ļ����Ƿ�������
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
		//����õ�֢״д�뵽diseaseall.txt�ļ���		
		FileUtil.appendData(ZhengDataSetConfig.AllZhengPath,newid+ZhengDataSetConfig.AttrSplit+zhengName.trim());
		
		return String.valueOf(newid);
}
}
