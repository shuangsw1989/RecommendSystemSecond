package net.wss.rs.data.zheng;

import junit.framework.TestCase;



public class TestZhengDoctorRecommendDataset extends TestCase{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		testDoctor();//测试获取的所有医生
//		testZheng();//测试获取的所有症状
//		testRating();//测试获取所有的评分
		testRatingMatrix();//测试所有用户的评分矩阵
	}
	
	public static void testDoctor(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset(ZhengDataSetConfig.AllDoctorPath,ZhengDataSetConfig.AllZhengPath,ZhengDataSetConfig.AllRatingPath);
		System.out.println("打印出所有的医生，具体如下:");
		ds.printAllDoctor();
	}
	public static void testZheng(){
//		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset();
		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset(ZhengDataSetConfig.AllDoctorPath,ZhengDataSetConfig.AllZhengPath,ZhengDataSetConfig.AllRatingPath);
		System.out.println("打印出所有症状，具体如下:");
		ds.printAllZheng();
	}
	public static void testRating(){
//		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset();
		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset(ZhengDataSetConfig.AllDoctorPath,ZhengDataSetConfig.AllZhengPath,ZhengDataSetConfig.AllRatingPath);
		System.out.println("打印出医生对症状的平，利用逗号分割症状的id与其评分，具体如下:");
		ds.printAllRating();
	}
	
	public static void testRatingMatrix(){
//		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset();
		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset(ZhengDataSetConfig.AllDoctorPath,ZhengDataSetConfig.AllZhengPath,ZhengDataSetConfig.AllRatingPath);
		System.out.println("打印医生对症状的评分矩阵，具体如下:");
		ds.printAllRatingMatrix();
	}

}
