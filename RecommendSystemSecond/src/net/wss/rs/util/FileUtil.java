package net.wss.rs.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class FileUtil {

	/**
	 * ׷������
	 * @param filePathAndName
	 * @param fileContent
	 */
	public static void appendData(String filePathAndName, String fileContent) {
        try {
            File myFilePath = new File(filePathAndName.toString());
            if (!myFilePath.exists()) { // ������ļ�������,�򴴽�
                myFilePath.createNewFile();
            }
            // FileWriter(myFilePath, true); ʵ�ֲ�����׷�ӵ��ļ���
             //FileWriter(myFilePath); ���ǵ�ԭ��������
            FileWriter resultFile = new FileWriter(myFilePath, true);
            PrintWriter myFile = new PrintWriter(resultFile);
            // ���ļ�����д����,ԭ���ĻḲ�ǵ�
            myFile.println(fileContent);
            resultFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
