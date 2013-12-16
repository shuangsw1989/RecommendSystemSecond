package net.wss.rs.recommend;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	 * 对一个医生所治疾病按照评分降序排列,取出前k个
	 * 
	 * k = 0 全部
	 * k > 0 取前K个
	 * k < 0 空
	 * @param doctor1
	 * @return
	 */
	public List<RatingEntity> ratingSortByDocId(int docId,int k){
		if(k < 0){
			return new ArrayList<RatingEntity>();
		}
		
		List<RatingEntity> docRatingList =ds.ratingsByDoctorId.get(docId);
		if(docRatingList==null){
			System.out.println("doctor:"+docId+"没有看病");
			return new ArrayList<RatingEntity>();
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
		List<RatingEntity> sortedDocRatingList =new ArrayList<RatingEntity>();
		int listLen = 0;
		if(k > 0 && docRatingIndexArray.length > k){
			listLen = k;
		}else{
			listLen = docRatingIndexArray.length;
		}
//		System.out.println(listLen);
		for (int i = 0; i < listLen; i++) {
			int docIdIndex = docRatingIndexArray[i];
			RatingEntity sortedRating = docRatingList.get(docIdIndex);
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
	 * 对所有医生按照评分降序排列，取出前k个疾病
	 * 第二种方法，有返回值
	 * @param k
	 * @return
	 */
	public Map<Integer, List<RatingEntity>> allRatingSort(int k){
		Map<Integer, List<RatingEntity>> sortedRatingsByDoctorId = new HashMap<Integer, List<RatingEntity>>();
		
		for (Entry<Integer, DoctorEntity> entry: ds.allDoctor.entrySet()) {
			Integer docKey = entry.getKey();//获取一个医生的id
//			System.out.print( "doc id:"+docKey+"  ");
			sortedRatingsByDoctorId.put(docKey, ratingSortByDocId(docKey,k));
		}
		
		return sortedRatingsByDoctorId;
	}
	
	/**
	 * 计算两个医生的相似度 不管诊次，只计算共同看的病的个数
	 */
	public int getSimilarityByCommonRating(DoctorEntity doctor1,
			DoctorEntity doctor2) {
//		System.out.println(doctor1.getId());
		List<RatingEntity> dis1 = ds.getSortedRatingsByDoctorId()
				.get(doctor1.getId());// 获取某一个医生所包含的疾病
				
		List<RatingEntity> dis2 = ds.getSortedRatingsByDoctorId()
				.get(doctor2.getId());

		// 如果这两个疾病集合有一个为空，则说明两个医生不相似，返回0
		int count = 0;
		if (dis1 == null || dis2 == null) {
			System.err.println("医生没有看过病");
			return 0;
		}

		// 判断两个疾病集合是否有交集，如果有则count+1
		for (int i = 0; i < dis1.size(); i++) {
			for (int j = 0; j < dis2.size(); j++) {
				if (dis1.get(i).getDiseaseId() == dis2.get(j).getDiseaseId()) {
					count++;
				}
			}
		}
		return count;
	}
	
	
	/**
	 * 计算所有医生的相似度 不管诊次，只计算共同看的病的个数
	 */
	public int[][] getAllSimilarityByCommonRating() {
		int allDoctorSize=ds.getAllDoctor().size();
//		System.out.println("allDoctorSize"+allDoctorSize);
		
		int[][] doctorSimilarity = new int[allDoctorSize+1][allDoctorSize+1];
	
		for (Entry<Integer, DoctorEntity> entry: ds.getAllDoctor().entrySet()) {
			DoctorEntity doc1 = entry.getValue();
			int i= doc1.getId();
			for (Entry<Integer, DoctorEntity> entry2: ds.getAllDoctor().entrySet()) {
				
				DoctorEntity doc2 = entry2.getValue();
				int j= doc2.getId();
				if (doc2.getId() > doc1.getId()) {
					continue;// 跳出本次循环，执行下次循环
				}
//				System.out.println(doc1.getId());
//				System.out.println(doc2.getId());
				int count = getSimilarityByCommonRating(doc1, doc2);// 找出相似的个数
				doctorSimilarity[i][j] = count;
				doctorSimilarity[j][i] = count;// 因为是个对称的
			}
		}
		return doctorSimilarity;
	}

/**
 * 将获得相似的矩阵，根据相似度排序，取出recommendDocNum个医生，放入sortedAllDocSim中，并给予推荐
 * @param doctorSimilarity 医生之间的相似度矩阵
 * @param recommendDocNum,推荐的数量
 * @return
 */
	public HashMap<Integer,String> recommendSimDoc(int[][] doctorSimilarity,int recommendDocNum) {
		HashMap<Integer,String> sortedAllDocSim = new HashMap<Integer,String>();
		for (int i = 1; i < doctorSimilarity.length; i++) {
			int[] doctorSim = doctorSimilarity[i];//取出一个医生与所有医生的相似度
			// 相似度排序
			int[] sortedDocSim = Sort.similaritySort(doctorSim);

			String docSimStr = "";
//			System.out.print("doc id:" + i + " ");
			int count = 0;
			int temp = 0;
			while (count < recommendDocNum && temp < sortedDocSim.length) {

				if (sortedDocSim[temp] == 0 || sortedDocSim[temp] == i || doctorSim[temp] <= 0) {//医生没有看病的情况
					temp++;
					continue;
				}
				//医生看过病，且将排序后的前recommendDocNum个，拼接成为一个字符串
				docSimStr += sortedDocSim[temp] + ",";
				// System.out.print(sortedDocSim[temp]+" ");
				temp++;
				count++;
			}
			if(docSimStr.length() >0){//去掉字符串最后的一个逗号
				docSimStr = docSimStr.substring(0, docSimStr.length() - 1);
			}
			
			// System.out.println();
//			System.out.println(docSimStr);
			sortedAllDocSim.put(i, docSimStr);//将医生id和推荐的字符串值，写入到map中
		}
		return sortedAllDocSim;
	}
	
	
	/**
	 * 将每个医生对疾病的评分按照降序排列
	 * 取出前5个
	 */
/*	public void ratingSort(int k){
		
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
		
	}*/
}
