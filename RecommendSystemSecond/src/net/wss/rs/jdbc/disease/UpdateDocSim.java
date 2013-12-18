package net.wss.rs.jdbc.disease;

import java.util.HashMap;
import java.util.Map.Entry;

import net.wss.rs.data.disease.DataSetConfig;
import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.recommend.DiseaseKNNSimilarity;

public class UpdateDocSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		推荐的相似医生数量
		int recommendDocNum = 10;
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
//		给ds中的sortedRatingsByDoctorId赋值
		knn.setAllRatingSort(30);
		
		int[][] doctorSimilarity = knn.getAllSimilarityByCommonRating();
		
		HashMap<Integer,String> sortedAllDocSim = knn.recommendSimDoc(doctorSimilarity, recommendDocNum);
		KbDoctorService kbservice = new KbDoctorService();
		for (Entry<Integer, String> entry: sortedAllDocSim.entrySet()) {
//			kbservice.updateDocSim(entry.getKey(), entry.getValue());
//			System.out.println("doc id="+entry.getKey()+"  "+entry.getValue());
		}
	}

}
