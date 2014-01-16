package net.wss.rs.recommend;

import junit.framework.TestCase;
import net.wss.rs.data.disease.DataSetConfig;
import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.util.Sort;

public class TestRecommendDoctor extends TestCase {
	public static void main(String[] args) {
		testTwoDoctorSimi();//两个用户的相似，与诊次无关
//		testTwoDoctorRatingSimi();//两个用户的相似，与诊次有关
//		testAllDoctorSimi();//所有用户相似，与诊次相关或不想关，用type来控制
	}
	
	
	public static void testTwoDoctorSimi(){
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		RecommendDoctor re =new RecommendDoctor(ds);
	
		DoctorEntity d1 = new DoctorEntity();
		d1.setId(2);
		DoctorEntity d2 = new DoctorEntity();
		d2.setId(3);
		
		int count = re.getSimilarityByCommonRating(d1, d2);
		System.out.println("医生"+d1.getId()+"与医生"+d2.getId()+"相似度"+count);
	}
	public static void testTwoDoctorRatingSimi(){
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		RecommendDoctor re =new RecommendDoctor(ds);
	
		DoctorEntity d1 = new DoctorEntity();
		d1.setId(2);
		DoctorEntity d2 = new DoctorEntity();
		d2.setId(3);
		
		int count1 = re.getSimilarityBySumCommonRating(d1, d2);
		System.out.println("医生"+d1.getId()+"与医生"+d2.getId()+"相似度"+count1);
		
	}
	public static void testAllDoctorSimi(){
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		RecommendDoctor re =new RecommendDoctor(ds);
		System.out.println("两个医生共同看的病的个数，对角线是这个医生一共看过多少病：");
		//0表示与诊次无关
		//1表示与诊次有关
		int[][] allDoctorSimilarity = re.getAllDoctorSimilarity(1);
		
		for(int docId=1; docId< allDoctorSimilarity.length;docId++){
			int[] doctorSim = allDoctorSimilarity[docId];//存入的是count
			int[] doctorSimIndexAsc=Sort.similaritySort(doctorSim);//存下标索引即用户的id
			
			System.out.println("与医生"+docId+"相似的结果，降序排列");
			for(int i=0; i< doctorSimIndexAsc.length;i++){
				System.out.print(doctorSimIndexAsc[i] + " ");
			}
			System.out.println();
			System.out.println("与医生"+docId+"最相似得前4个医生");
			int kSim =10+1;
			for(int i=0; i<kSim ;i++){
				
				System.out.print(doctorSimIndexAsc[i] + " ");
			}
			System.out.println();
		}
	}
}
