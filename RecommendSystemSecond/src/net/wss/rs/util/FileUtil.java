package net.wss.rs.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class FileUtil {

	/**
	 * 追加数据
	 * @param filePathAndName
	 * @param fileContent
	 */
	public static void appendData(String filePathAndName, String fileContent) {
        try {
            File myFilePath = new File(filePathAndName.toString());
            if (!myFilePath.exists()) { // 如果该文件不存在,则创建
                myFilePath.createNewFile();
            }
            // FileWriter(myFilePath, true); 实现不覆盖追加到文件里
             //FileWriter(myFilePath); 覆盖掉原来的内容
            FileWriter resultFile = new FileWriter(myFilePath, true);
            PrintWriter myFile = new PrintWriter(resultFile);
            // 给文件里面写内容,原来的会覆盖掉
            myFile.println(fileContent);
            resultFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
