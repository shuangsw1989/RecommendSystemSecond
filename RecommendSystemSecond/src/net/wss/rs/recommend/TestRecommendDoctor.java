package net.wss.rs.recommend;

import junit.framework.TestCase;
import net.wss.rs.data.disease.DataSetConfig;
import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.util.Sort;

public class TestRecommendDoctor extends TestCase {
	public static void main(String[] args) {
		testTwoDoctorSimi();//�����û������ƣ�������޹�
//		testTwoDoctorRatingSimi();//�����û������ƣ�������й�
//		testAllDoctorSimi();//�����û����ƣ��������ػ���أ���type������
	}
	
	
	public static void testTwoDoctorSimi(){
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		RecommendDoctor re =new RecommendDoctor(ds);
	
		DoctorEntity d1 = new DoctorEntity();
		d1.setId(2);
		DoctorEntity d2 = new DoctorEntity();
		d2.setId(3);
		
		int count = re.getSimilarityByCommonRating(d1, d2);
		System.out.println("ҽ��"+d1.getId()+"��ҽ��"+d2.getId()+"���ƶ�"+count);
	}
	public static void testTwoDoctorRatingSimi(){
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		RecommendDoctor re =new RecommendDoctor(ds);
	
		DoctorEntity d1 = new DoctorEntity();
		d1.setId(2);
		DoctorEntity d2 = new DoctorEntity();
		d2.setId(3);
		
		int count1 = re.getSimilarityBySumCommonRating(d1, d2);
		System.out.println("ҽ��"+d1.getId()+"��ҽ��"+d2.getId()+"���ƶ�"+count1);
		
	}
	public static void testAllDoctorSimi(){
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		RecommendDoctor re =new RecommendDoctor(ds);
		System.out.println("����ҽ����ͬ���Ĳ��ĸ������Խ��������ҽ��һ���������ٲ���");
		//0��ʾ������޹�
		//1��ʾ������й�
		int[][] allDoctorSimilarity = re.getAllDoctorSimilarity(1);
		
		for(int docId=1; docId< allDoctorSimilarity.length;docId++){
			int[] doctorSim = allDoctorSimilarity[docId];//�������count
			int[] doctorSimIndexAsc=Sort.similaritySort(doctorSim);//���±��������û���id
			
			System.out.println("��ҽ��"+docId+"���ƵĽ������������");
			for(int i=0; i< doctorSimIndexAsc.length;i++){
				System.out.print(doctorSimIndexAsc[i] + " ");
			}
			System.out.println();
			System.out.println("��ҽ��"+docId+"�����Ƶ�ǰ4��ҽ��");
			int kSim =10+1;
			for(int i=0; i<kSim ;i++){
				
				System.out.print(doctorSimIndexAsc[i] + " ");
			}
			System.out.println();
		}
	}
}
