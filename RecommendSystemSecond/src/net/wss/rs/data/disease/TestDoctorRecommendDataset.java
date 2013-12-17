package net.wss.rs.data.disease;

public class TestDoctorRecommendDataset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testDoctor();//测试获取的所有医生
		testDisease();//测试获取的所有疾病
		testRating();//测试获取所有的评分
		testRatingMatrix();//测试所有用户的评分矩阵
	}
	
	public static void testDoctor(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		System.out.println("打印出所有的医生，具体如下:");
		ds.printAllDoctor();
	}
	public static void testDisease(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		System.out.println("打印出所有疾病，具体如下:");
		ds.printAllDisease();
	}
	public static void testRating(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		System.out.println("打印出医生对疾病的平，利用逗号分割疾病的id与其评分，具体如下:");
		ds.printAllRating();
	}
	
	public static void testRatingMatrix(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		System.out.println("打印医生对疾病的评分矩阵，具体如下:");
		ds.printAllRatingMatrix();
	}

}
