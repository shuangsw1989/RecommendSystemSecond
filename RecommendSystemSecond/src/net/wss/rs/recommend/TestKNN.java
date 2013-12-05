package net.wss.rs.recommend;

import net.wss.rs.confi.Constant;
import net.wss.rs.data.DoctorRecommendDataset;

public class TestKNN {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
		
		knn.ratingSort(Constant.biggestratingnum);
	}
}
