package net.wss.rs.data.disease;

public class DataSetConfig {
	
	//将数据库获得的数据文件，进行行处理，转换为三个需要的文件
	public static String AllRatingPath = "data/diseasedata/ratingall.txt";
	public static String AllDoctorPath = "data/diseasedata/doctorall.txt";
	public static String AllDiseasePath = "data/diseasedata/diseaseall.txt";
	public static String RecommendResultPath="data/diseasedata/recoresult.txt";
	//控制字符串分割的方式
	public static String AttrSplit = ";";
	//读取数据库获得的数据文件
	public static String AllDataPath = "data/diseasedata/alldata.txt";
	public static String SimiResultPath ="data/diseasedata/allsimi.txt";
}
