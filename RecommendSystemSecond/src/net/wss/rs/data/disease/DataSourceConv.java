package net.wss.rs.data.disease;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.sun.faces.util.Util;

import net.wss.rs.jdbc.disease.ReadDBTable;
import net.wss.rs.util.FileUtil;
import net.wss.rs.util.StringUtil;

public class DataSourceConv {
	
	/**
	 * ��alldata���ݼ������������ּ����Ӧ�����
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
						//��������ݵ�ǰ����д�뵽dotorall.txt��
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
					 
					
					String[] diseaseArray = disInfo.split(",");// ����ȡ���ַ����ָ����һ��������
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
							disId = getDiseaseId(disName);//���÷�����ü�����id
							rating= disNameRatingArray[1].trim();
							//��ҽ��id,����id,���Ӧ��������Σ�д��ratingall.txt�ļ���
							FileUtil.appendData(DataSetConfig.AllRatingPath,docInfo[0]+ DataSetConfig.AttrSplit+disId+DataSetConfig.AttrSplit+rating);
//							System.out.print(disName[0] + " " + disName[1]);
				/**
				 * ��������alldata.txt�ļ��У����ݸ�ʽ�д����ʱ��ʹ���������Щ����ȥ����
				 * ������������ݼ����������⣬���������if����д����ص��ļ�		
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
							//��ҽ��id,����id,���Ӧ��������Σ�д��ratingall.txt�ļ���
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
	 * ��ȡ������id,
	 * ���ж��ظ�����д�룬Ȼ����丳һ��id
	 * �������ж�diseaseall.txt�ļ����Ƿ�������
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
		//����õļ���д�뵽diseaseall.txt�ļ���		
		FileUtil.appendData(DataSetConfig.AllDiseasePath,newid+DataSetConfig.AttrSplit+disName.trim());
		
		return String.valueOf(newid);
		
	}
}
