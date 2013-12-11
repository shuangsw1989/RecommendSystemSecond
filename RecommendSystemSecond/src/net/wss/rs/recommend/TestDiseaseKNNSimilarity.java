package net.wss.rs.recommend;

import net.wss.rs.confi.Constant;
import net.wss.rs.data.DataSetConfig;
import net.wss.rs.data.DoctorRecommendDataset;

public class TestDiseaseKNNSimilarity {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
		
		knn.ratingSortByDocId(2,2);
		
//		knn.ratingSort(Constant.biggestratingnum);
		
	}
}
