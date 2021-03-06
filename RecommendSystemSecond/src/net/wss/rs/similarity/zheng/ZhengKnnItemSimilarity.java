package net.wss.rs.similarity.zheng;

import java.util.List;
import java.util.Map.Entry;
import net.wss.rs.data.zheng.ZhengDoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.ZhengRatingEntity;


public class ZhengKnnItemSimilarity {
	
	
	/**
	 * 计算两个医生的相似度 不管诊次，只计算共同看的病的个数
	 */
	public int getSimilarityByCommonRating(ZhengDoctorRecommendDataset ds ,DoctorEntity doctor1,
			DoctorEntity doctor2) {
//		System.out.println(doctor1.getId());
		List<ZhengRatingEntity> zheng1 = ds.getSortedRatingsByDoctorId()
				.get(doctor1.getId());// 获取某一个医生所包含的症状
				
		List<ZhengRatingEntity> zheng2 = ds.getSortedRatingsByDoctorId()
				.get(doctor2.getId());

		// 如果这两个症状集合有一个为空，则说明两个医生不相似，返回0
		int count = 0;
		if (zheng1 == null || zheng2 == null) {
			System.err.println("医生没有看过病");
			return 0;
		}

		// 判断两个症状集合是否有交集，如果有则count+1
		for (int i = 0; i < zheng1.size(); i++) {
			for (int j = 0; j < zheng2.size(); j++) {
				if (zheng1.get(i).getZhengId() == zheng2.get(j).getZhengId()) {
					count++;
				}
			}
		}
		return count;
	}
	
	
	/**
	 * 计算所有医生的相似度 不管诊次，只计算共同看的病的个数
	 */
	public int[][] getAllSimilarityByCommonRating(ZhengDoctorRecommendDataset ds) {
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
				int count = getSimilarityByCommonRating(ds,doc1, doc2);// 找出相似的个数
				doctorSimilarity[i][j] = count;
				doctorSimilarity[j][i] = count;// 因为是个对称的
			}
		}
		return doctorSimilarity;
	}


	
	

}
