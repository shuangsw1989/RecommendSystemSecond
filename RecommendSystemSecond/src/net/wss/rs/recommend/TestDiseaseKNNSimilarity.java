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
		
//		testSort();//对一个医生所治疾病的诊次进行排序
//		testAllSort();//对所有医生所治疾病的诊次进行排序
//		testSim();//测试两个医生的相似度
		testRecommendSimDoc();//测试推荐相似医生
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
	
	public static void testRecommendSimDoc(){
//		推荐的相似医生数量
		int recommendDocNum = 10;
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
//		给ds中的sortedRatingsByDoctorId赋值
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
		Map<Integer, List<DiseaseRatingEntity>> sortedRatingsByDoctorId = knn.allRatingSort(5);
		
		for (Entry<Integer, List<DiseaseRatingEntity>> entry: sortedRatingsByDoctorId.entrySet()) {
			 List<DiseaseRatingEntity>	sortedDocRatingList = entry.getValue();
			 for (int i = 0; i < sortedDocRatingList.size(); i++) {						 
				 System.out.print("医生"+sortedDocRatingList.get(i).getDoctorId()+"治疗疾病"+sortedDocRatingList.get(i).getDiseaseId()+"的诊次是:"+sortedDocRatingList.get(i).getRating() + " " );	
			 }
			 System.out.println();	
			 }
		}
	}
