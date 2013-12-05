package net.wss.rs.recommend;


import java.util.List;
import java.util.Map.Entry;

import net.wss.rs.data.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.RatingEntity;
import net.wss.rs.util.Sort;

public class DiseaseKNNSimilarity {

	
	DoctorRecommendDataset ds;

	public DiseaseKNNSimilarity(DoctorRecommendDataset ds) {
		this.ds = ds;
	}
	
	

	/**
	 * 将每个医生对疾病的评分按照降序排列

	 */
	public void ratingSort(int k){
		
		for (Entry<Integer, DoctorEntity> entry: ds.allDoctor.entrySet()) {
			Integer docKey = entry.getKey();
			System.out.print( "doc id:"+docKey+"  ");
			List<RatingEntity> docRatingList = ds.ratingsByDoctorId.get(docKey);
			
//			转换成array
//			if(docRatingList.size()){
//				
//			}
//			Integer[] docRatingArray = new Integer[];
//			for()
			if(docRatingList == null){
				System.out.println();
				continue;
			}
			
			RatingEntity[] docRatingArray = new  RatingEntity[docRatingList.size()];
			docRatingList.toArray(docRatingArray);
			
			int[] docRatingValueArray = new int[docRatingArray.length];
			for (int i=0; i<docRatingArray.length;i++) {
				docRatingValueArray[i] = docRatingArray[i].getRating();
			}
			
			int[] docRatingIndexArray  = Sort.similaritySort(docRatingValueArray);
			
//			int K = 3;
			int size =0;
			if(docRatingList.size() > k){
				size = k;
			}else{
				size = docRatingList.size();
			}
			
			for (int i = 0; i < size; i++) {
				System.out.print(docRatingList.get(docRatingIndexArray[i]).getDiseaseId()+"  ");
			}
			System.out.println();

			
		}
		
	}
}
