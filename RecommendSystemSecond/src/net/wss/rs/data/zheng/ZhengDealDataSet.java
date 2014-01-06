package net.wss.rs.data.zheng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.ZhengRatingEntity;
import net.wss.rs.util.Sort;

public class ZhengDealDataSet {
	
	ZhengDoctorRecommendDataset ds;
	
	
	
	public ZhengDealDataSet(ZhengDoctorRecommendDataset ds) {
		super();
		this.ds = ds;
	}


	/**
	 * 对一个医生所治症状按照评分降序排列,取出前k个
	 * 
	 * k = 0 全部
	 * k > 0 取前K个
	 * k < 0 空
	 * @param doctor1
	 * @return
	 */
	public List<ZhengRatingEntity> ratingSortByDocId(int docId,int k){
		if(k < 0){
			return new ArrayList<ZhengRatingEntity>();
		}
		
		List<ZhengRatingEntity> docRatingList =ds.ratingsByDoctorId.get(docId);
		if(docRatingList==null){
			System.out.println("doctor:"+docId+"没有看病");
			return new ArrayList<ZhengRatingEntity>();
		}
		//将doc看的病list集合转换成array
//		RatingEntity[] docRatingArray = new  RatingEntity[docRatingList.size()];
//		docRatingList.toArray(docRatingArray);		
		//取出医生的评分，放入数组
//		int[] docRatingValueArray = new int[docRatingArray.length];
//		for (int i=0; i<docRatingArray.length;i++) {
//			docRatingValueArray[i] = docRatingArray[i].getRating();
//		}

		//取出医生的评分，放入数组
		int[] docRatingValueArray = new int[docRatingList.size()];
		for (int i=0; i<docRatingValueArray.length;i++) {
			docRatingValueArray[i] = docRatingList.get(i).getRating();
		}
		//对评分排序，返回索引
		int[] docRatingIndexArray  = Sort.similaritySort(docRatingValueArray);
		
		//根据下标在list中找出相应的实体
		List<ZhengRatingEntity> sortedDocRatingList =new ArrayList<ZhengRatingEntity>();
		int listLen = 0;
		if(k > 0 && docRatingIndexArray.length > k){
			listLen = k;
		}else{
			listLen = docRatingIndexArray.length;
		}
//		System.out.println(listLen);
		for (int i = 0; i < listLen; i++) {
			int docIdIndex = docRatingIndexArray[i];
			ZhengRatingEntity sortedRating = docRatingList.get(docIdIndex);
			sortedDocRatingList.add(sortedRating);	
		}
		
//		for (int i = 0; i < sortedDocRatingList.size(); i++) {		
//			System.out.print(sortedDocRatingList.get(i).getDiseaseId()+":"+sortedDocRatingList.get(i).getRating() + " " );
//		}		
//		System.out.println();
		
		return sortedDocRatingList;
	}
	
	
	/**
	 * 对所有医生按照评分降序排列
	 * 第一种方法，无返回值
	 * @param k  取前k个
	 * @return
	 */
	public void setAllRatingSort(int k){
//		Map<Integer, List<RatingEntity>> sortedRatingsByDoctorId;
		
		for (Entry<Integer, DoctorEntity> entry: ds.allDoctor.entrySet()) {
			Integer docKey = entry.getKey();//获取一个医生的id
//			System.out.print( "doc id:"+docKey+"  ");
			 ds.sortedRatingsByDoctorId.put(docKey, ratingSortByDocId(docKey,k));
		}		
		
	}
	/**
	 * 对所有医生按照评分降序排列，取出前k个症状
	 * 第二种方法，有返回值
	 * @param k
	 * @return
	 */
	public Map<Integer, List<ZhengRatingEntity>> allRatingSort(int k){
		Map<Integer, List<ZhengRatingEntity>> sortedRatingsByDoctorId = new HashMap<Integer, List<ZhengRatingEntity>>();
		
		for (Entry<Integer, DoctorEntity> entry: ds.allDoctor.entrySet()) {
			Integer docKey = entry.getKey();//获取一个医生的id
//			System.out.print( "doc id:"+docKey+"  ");
			sortedRatingsByDoctorId.put(docKey, ratingSortByDocId(docKey,k));
		}
		
		return sortedRatingsByDoctorId;
	}
	
}
