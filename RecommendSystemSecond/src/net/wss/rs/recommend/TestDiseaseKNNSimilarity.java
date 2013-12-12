package net.wss.rs.recommend;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.wss.rs.data.DataSetConfig;
import net.wss.rs.data.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.RatingEntity;


public class TestDiseaseKNNSimilarity {
	public static void main(String[] args) {
		
		testSort();//对一个医生所治疾病的诊次进行排序
//		testAllSort();//对所有医生所治疾病的诊次进行排序
//		testSim();//测试两个医生的相似度
//		testAllSim();//测试所有医生的相似度
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
		System.out.println("doc"+d1.getId()+"与doc"+d2.getId()+"相似度："+simVal);
	}
	
	public static void testAllSim(){
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
		knn.setAllRatingSort(100);
		
		int[][] doctorSimilarity = knn.getAllSimilarityByCommonRating();
	
		for (int i = 0; i < doctorSimilarity.length; i++) {
			for (int j = 0; j < doctorSimilarity[i].length; j++) {
				System.out.print(doctorSimilarity[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	
	
	public static void testSort(){
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
		List<RatingEntity> list = knn.ratingSortByDocId(2,2);
		for(int i=0;i<list.size();i++){
			System.out.println("医生"+list.get(i).getDoctorId()+"对"+list.get(i).getDiseaseId()+"的诊次是："+list.get(i).getRating());
		}
	}
	
	public static void testAllSort(){
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
		
		//更改数据源 设置排序
//		Map<Integer, List<RatingEntity>> sortedRatingsByDoctorId = new HashMap<Integer, List<RatingEntity>>();
//		knn.setAllRatingSort(2);
//		sortedRatingsByDoctorId = ds.getSortedRatingsByDoctorId();		
		//直接获取结果
		Map<Integer, List<RatingEntity>> sortedRatingsByDoctorId = knn.allRatingSort(2);
		
		for (Entry<Integer, List<RatingEntity>> entry: sortedRatingsByDoctorId.entrySet()) {
			 List<RatingEntity>	sortedDocRatingList = entry.getValue();
			 for (int i = 0; i < sortedDocRatingList.size(); i++) {						 
				 System.out.print(sortedDocRatingList.get(i).getDiseaseId()+":"+sortedDocRatingList.get(i).getRating() + " " );	
			 }
			 System.out.println();	
			 }
		}
	}
