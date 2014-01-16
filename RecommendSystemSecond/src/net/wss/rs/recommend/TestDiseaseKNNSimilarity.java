package net.wss.rs.recommend;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;

import net.wss.rs.data.disease.DataSetConfig;
import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.DiseaseRatingEntity;
import net.wss.rs.util.Sort;


public class TestDiseaseKNNSimilarity extends TestCase{
	public static void main(String[] args) {
		
//		testSort();//��һ��ҽ�����μ�������ν�������
//		testAllSort();//������ҽ�����μ�������ν�������
//		testSim();//��������ҽ�������ƶ�
		testRecommendSimDoc();//�����Ƽ�����ҽ��
	}
	
	public static void testSim(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
		knn.setAllRatingSort(100);
		
		DoctorEntity d1 = new DoctorEntity();
		d1.setId(2);
		DoctorEntity d2 = new DoctorEntity();
		d2.setId(3);
		int simVal = knn.getSimilarityByCommonRating(d1, d2);
		System.out.println("doc"+d1.getId()+"��doc"+d2.getId()+"���ƶȣ�"+simVal);
	}
	
	public static void testRecommendSimDoc(){
//		�Ƽ�������ҽ������
		int recommendDocNum = 10;
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
//		��ds�е�sortedRatingsByDoctorId��ֵ
		knn.setAllRatingSort(30);
		
		int[][] doctorSimilarity = knn.getAllSimilarityByCommonRating();
		
		HashMap<Integer,String> sortedAllDocSim = knn.recommendSimDoc(doctorSimilarity, recommendDocNum);
		
		for (Entry<Integer, String> entry: sortedAllDocSim.entrySet()) {
			System.out.println("doc id="+entry.getKey()+"  "+entry.getValue());
		}
		
	}
	
	
	
	public static void testSort(){
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
		List<DiseaseRatingEntity> list = knn.ratingSortByDocId(2,5);
		for(int i=0;i<list.size();i++){
			System.out.println("ҽ��"+list.get(i).getDoctorId()+"��"+list.get(i).getDiseaseId()+"������ǣ�"+list.get(i).getRating());
		}
	}
	
	public static void testAllSort(){
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
		
		//��������Դ ��������
//		Map<Integer, List<RatingEntity>> sortedRatingsByDoctorId = new HashMap<Integer, List<RatingEntity>>();
//		knn.setAllRatingSort(2);
//		sortedRatingsByDoctorId = ds.getSortedRatingsByDoctorId();		
		//ֱ�ӻ�ȡ���
		Map<Integer, List<DiseaseRatingEntity>> sortedRatingsByDoctorId = knn.allRatingSort(5);
		
		for (Entry<Integer, List<DiseaseRatingEntity>> entry: sortedRatingsByDoctorId.entrySet()) {
			 List<DiseaseRatingEntity>	sortedDocRatingList = entry.getValue();
			 for (int i = 0; i < sortedDocRatingList.size(); i++) {						 
				 System.out.print("ҽ��"+sortedDocRatingList.get(i).getDoctorId()+"���Ƽ���"+sortedDocRatingList.get(i).getDiseaseId()+"�������:"+sortedDocRatingList.get(i).getRating() + " " );	
			 }
			 System.out.println();	
			 }
		}
	}
