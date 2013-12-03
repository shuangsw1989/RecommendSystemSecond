package net.wss.rs.re;

import java.util.List;
import java.util.Map;

import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.RatingEntity;
import net.wss.rs.text.DoctorRecommendDataset;

public class RecommendDoctor {
	DoctorRecommendDataset ds ;
	public RecommendDoctor(DoctorRecommendDataset ds) {
		this.ds=ds;
	}
	
	/**
	 * 计算两个用户的相似度
	 * 不管诊次，只计算共同看的病的个数
	 */
	public int getSimilarityByCommonRating(DoctorEntity doctor1, DoctorEntity doctor2) {
		List<RatingEntity> dis1 = ds.getRatingsByDoctorId().get(doctor1.getId());// 获取某一个用户所包含的疾病
		List<RatingEntity> dis2 = ds.getRatingsByDoctorId().get(doctor2.getId());
		
		// 如果这两个疾病集合有一个为空，则说明两个用户不相似，返回0
		int count = 0;
		if (dis1 == null || dis2 == null) {
			System.err.println("用户不存在");
			return 0;
			
		}

		
		// 判断两个疾病集合是否有交集，如果有则count+1
	
		for(int i =0;i<dis1.size();i++){
			for(int j =0;j<dis2.size();j++){
				if(dis1.get(i).getDiseaseId()==dis2.get(j).getDiseaseId()){
					count++;				
				}
			}
		}
		return count;
	}
	
	
	/**
	 * 计算两个用户的相似度
	 * 不管诊次，只计算共同看的病的个数
	 */
	public int getSimilarityBySumCommonRating(DoctorEntity doctor1, DoctorEntity doctor2) {
		List<RatingEntity> dis1 = ds.getRatingsByDoctorId().get(doctor1.getId());// 获取某一个用户所包含的疾病
		List<RatingEntity> dis2 = ds.getRatingsByDoctorId().get(doctor2.getId());
		
		// 如果这两个疾病集合有一个为空，则说明两个用户不相似，返回0
		if (dis1 == null || dis2 == null) {
			System.err.println("用户不存在");
			return 0;
			
		}

		
		// 判断两个疾病集合是否有交集，如果有则count+1
		int min=0;
		for(int i =0;i<dis1.size();i++){
			for(int j =0;j<dis2.size();j++){
				if(dis1.get(i).getDiseaseId()==dis2.get(j).getDiseaseId()){
					int dis1Rating = dis1.get(i).getRating();
					int dis2Rating = dis2.get(j).getRating();
					if(dis1Rating>dis2Rating){
					  min = min+dis2Rating;						
					}else{
						min = min+dis1Rating;
					}			
				}
			}
		}
		return min;
	}
	
	/**
	 * 得到所有医生治疗相同的疾病的个数（比较）
	 * 
	 * @param allDisease
	 * @param allDoctor
	 * @param diseaseByDoctorId
	 * 
	 */
//	public void getAllDoctorSimilartiy(
//			List<DiseaseEntity> allDisease, List<DoctorEntity> allDoctor,
//			Map<Integer, List<Integer>> diseaseByDoctorId) {
//
//		int[][] doctorSimilarity = new int[allDoctor.size()][allDoctor.size()];
//		for (int i = 0; i < allDoctor.size(); i++) {
//			DoctorEntity doc1 = allDoctor.get(i);
//			for (int j = 0; j < allDoctor.size(); j++) {
//				DoctorEntity doc2 = allDoctor.get(j);
//				if (i > j) {
//					continue;// 跳出本次循环，执行下次循环
//				}
//				int count = getSimilarity(doc1, doc2, diseaseByDoctorId);// 找出相似的个数
//				doctorSimilarity[i][j] = count;
//				doctorSimilarity[j][i] = count;// 因为是个对称的
//			}
//		}
//	}
	
	
	
	
}
