package net.wss.rs.recommend;

import java.util.List;

import java.util.Map.Entry;


import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.DiseaseRatingEntity;

public class RecommendDoctor {
	DoctorRecommendDataset ds;

	public RecommendDoctor(DoctorRecommendDataset ds) {
		this.ds = ds;
	}

	/**
	 * 计算两个医生的相似度 不管诊次，只计算共同看的病的个数
	 */
	public int getSimilarityByCommonRating(DoctorEntity doctor1,
			DoctorEntity doctor2) {
//		System.out.println(doctor1.getId());
		List<DiseaseRatingEntity> dis1 = ds.getRatingsByDoctorId()
				.get(doctor1.getId());// 获取某一个医生所包含的疾病
		List<DiseaseRatingEntity> dis2 = ds.getRatingsByDoctorId()
				.get(doctor2.getId());

		// 如果这两个疾病集合有一个为空，则说明两个医生不相似，返回0
		int count = 0;
		if (dis1 == null || dis2 == null) {
//			System.err.println("医生没有看过病");
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
		
		for (int i = 0; i < doctorSimilarity.length; i++) {
			for (int j = 0; j < doctorSimilarity[i].length; j++) {
				System.out.print(doctorSimilarity[i][j] + " ");
			}
			System.out.println();
		}
		return doctorSimilarity;
	}

	/**
	 * 计算两个医生的相似度 ，返回两个医生对共同疾病诊次的和的最小值
	 */
	public int getSimilarityBySumCommonRating(DoctorEntity doctor1,
			DoctorEntity doctor2) {
		List<DiseaseRatingEntity> dis1 = ds.getRatingsByDoctorId()
				.get(doctor1.getId());// 获取某一个医生所包含的疾病
		List<DiseaseRatingEntity> dis2 = ds.getRatingsByDoctorId()
				.get(doctor2.getId());
		
		// 如果这两个疾病集合有一个为空，则说明两个医生不相似，返回0
		if (dis1 == null || dis2 == null) {
//			System.err.println("医生没有治过病");
			return 0;
		}

		// 判断两个疾病集合是否有交集，如果有则count+1
		int min = 0;
		for (int i = 0; i < dis1.size(); i++) {
			for (int j = 0; j < dis2.size(); j++) {
				if (dis1.get(i).getDiseaseId() == dis2.get(j).getDiseaseId()) {
					int dis1Rating = dis1.get(i).getRating();
					int dis2Rating = dis2.get(j).getRating();
					if (dis1Rating > dis2Rating) {
						min = min + dis2Rating;
					} else {
						min = min + dis1Rating;
					}
				}
			}
		}
		return min;
	}
	
	/**
	 * 计算所有医生的相似度
	 */
	public int[][] getAllDoctorSimilarity(int type) {
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
				int count=0;
				if(type ==0){
					count = getSimilarityByCommonRating(doc1, doc2);// 找出相似的个数
				}else if(type==1){
					count = getSimilarityBySumCommonRating(doc1, doc2);// 找出相似的个数
				}else{
					System.out.println("error");
					return null;
				}
				
				doctorSimilarity[i][j] = count;
				doctorSimilarity[j][i] = count;// 因为是个对称的
			}
		}
		
		for (int i = 0; i < doctorSimilarity.length; i++) {
			for (int j = 0; j < doctorSimilarity[i].length; j++) {
				System.out.print(doctorSimilarity[i][j] + " ");
			}
			System.out.println();
		}
		
		return doctorSimilarity;
	}
}
