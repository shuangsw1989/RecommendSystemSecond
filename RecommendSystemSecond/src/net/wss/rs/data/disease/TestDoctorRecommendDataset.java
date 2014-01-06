package net.wss.rs.data.disease;

public class TestDoctorRecommendDataset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		testDoctor();//���Ի�ȡ������ҽ��
//		testDisease();//���Ի�ȡ�����м���
//		testRating();//���Ի�ȡ���е�����
		testRatingMatrix();//���������û������־���
	}
	
	public static void testDoctor(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		System.out.println("��ӡ�����е�ҽ������������:");
		ds.printAllDoctor();
	}
	public static void testDisease(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		System.out.println("��ӡ�����м�������������:");
		ds.printAllDisease();
	}
	public static void testRating(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		System.out.println("��ӡ��ҽ���Լ�����ƽ�����ö��ŷָ����id�������֣���������:");
		ds.printAllRating();
	}
	
	public static void testRatingMatrix(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		System.out.println("��ӡҽ���Լ��������־��󣬾�������:");
		ds.printAllRatingMatrix();
	}

}
