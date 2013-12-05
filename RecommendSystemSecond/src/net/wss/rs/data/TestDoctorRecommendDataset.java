package net.wss.rs.data;

public class TestDoctorRecommendDataset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		System.out.println("打印出所有的医生，具体如下:");
		ds.printAllDoctor();
		System.out.println("打印出所有疾病，具体如下:");
		ds.printAllDisease();
		System.out.println("打印出医生对疾病的平，利用逗号分割疾病的id与其评分，具体如下:");
		ds.printAllRating();
		System.out.println("打印医生对疾病的评分矩阵，具体如下:");
		ds.printAllRatingMatrix();
	
		
		
		
		
		
	}
	
	
	
	

}
