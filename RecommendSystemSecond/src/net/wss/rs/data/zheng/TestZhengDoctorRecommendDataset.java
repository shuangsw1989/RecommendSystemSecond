package net.wss.rs.data.zheng;

import junit.framework.TestCase;



public class TestZhengDoctorRecommendDataset extends TestCase{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		testDoctor();//���Ի�ȡ������ҽ��
//		testZheng();//���Ի�ȡ������֢״
//		testRating();//���Ի�ȡ���е�����
		testRatingMatrix();//���������û������־���
	}
	
	public static void testDoctor(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset(ZhengDataSetConfig.AllDoctorPath,ZhengDataSetConfig.AllZhengPath,ZhengDataSetConfig.AllRatingPath);
		System.out.println("��ӡ�����е�ҽ������������:");
		ds.printAllDoctor();
	}
	public static void testZheng(){
//		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset();
		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset(ZhengDataSetConfig.AllDoctorPath,ZhengDataSetConfig.AllZhengPath,ZhengDataSetConfig.AllRatingPath);
		System.out.println("��ӡ������֢״����������:");
		ds.printAllZheng();
	}
	public static void testRating(){
//		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset();
		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset(ZhengDataSetConfig.AllDoctorPath,ZhengDataSetConfig.AllZhengPath,ZhengDataSetConfig.AllRatingPath);
		System.out.println("��ӡ��ҽ����֢״��ƽ�����ö��ŷָ�֢״��id�������֣���������:");
		ds.printAllRating();
	}
	
	public static void testRatingMatrix(){
//		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset();
		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset(ZhengDataSetConfig.AllDoctorPath,ZhengDataSetConfig.AllZhengPath,ZhengDataSetConfig.AllRatingPath);
		System.out.println("��ӡҽ����֢״�����־��󣬾�������:");
		ds.printAllRatingMatrix();
	}

}
