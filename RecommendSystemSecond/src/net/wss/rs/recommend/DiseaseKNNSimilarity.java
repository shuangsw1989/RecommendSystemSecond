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
	 * 对一个医生所治疾病按照评分降序排列
	 * 
	 * @param doctor1
	 * @return
	 */
	public int[] ratingSortByDocId(int docId,int k){
		List<RatingEntity> docRatingList =ds.ratingsByDoctorId.get(docId);
		if(docRatingList==null){
			System.out.println();
		}
		RatingEntity[] docRatingArray = new  RatingEntity[docRatingList.size()];
		docRatingList.toArray(docRatingArray);
		
		//取出医生的评分
		int[] docRatingValueArray = new int[docRatingArray.length];
		for (int i=0; i<docRatingArray.length;i++) {
			docRatingValueArray[i] = docRatingArray[i].getRating();
		}
		//对评分排序，返回索引
		int[] docRatingIndexArray  = Sort.similaritySort(docRatingValueArray);
		
		for (int i = 0; i < docRatingIndexArray.length; i++) {
			int j = docRatingIndexArray[i];
			System.out.println(j);
		}
		
		return docRatingIndexArray;

	}
	

	/**
	 * 将每个医生对疾病的评分按照降序排列
	 * 取出前5个
	 */
	public void ratingSort(int k){
		
		for (Entry<Integer, DoctorEntity> entry: ds.allDoctor.entrySet()) {
			Integer docKey = entry.getKey();//获取一个医生的id
			System.out.print( "doc id:"+docKey+"  ");
			List<RatingEntity> docRatingList =ds.ratingsByDoctorId.get(docKey);
			//根据id得到一个医生的所有疾病的诊次的集合
//			int[] docRatingIndexArray = ratingSortByDocId(docKey);
			
			
			
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
			//将这个集合转换成数组的形式
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
				System.out.print("疾病的id:"+docRatingList.get(docRatingIndexArray[i]).getDiseaseId()+"  ");
			}
			System.out.println();
		}
		
	}
}
