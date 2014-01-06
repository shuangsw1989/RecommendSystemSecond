package net.wss.rs.similarity.zheng;

import java.util.List;
import java.util.Map.Entry;
import net.wss.rs.data.zheng.ZhengDoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.ZhengRatingEntity;


public class ZhengKnnItemSimilarity {
	
	
	/**
	 * ��������ҽ�������ƶ� ������Σ�ֻ���㹲ͬ���Ĳ��ĸ���
	 */
	public int getSimilarityByCommonRating(ZhengDoctorRecommendDataset ds ,DoctorEntity doctor1,
			DoctorEntity doctor2) {
//		System.out.println(doctor1.getId());
		List<ZhengRatingEntity> zheng1 = ds.getSortedRatingsByDoctorId()
				.get(doctor1.getId());// ��ȡĳһ��ҽ����������֢״
				
		List<ZhengRatingEntity> zheng2 = ds.getSortedRatingsByDoctorId()
				.get(doctor2.getId());

		// ���������֢״������һ��Ϊ�գ���˵������ҽ�������ƣ�����0
		int count = 0;
		if (zheng1 == null || zheng2 == null) {
			System.err.println("ҽ��û�п�����");
			return 0;
		}

		// �ж�����֢״�����Ƿ��н������������count+1
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
	 * ��������ҽ�������ƶ� ������Σ�ֻ���㹲ͬ���Ĳ��ĸ���
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
					continue;// ��������ѭ����ִ���´�ѭ��
				}
//				System.out.println(doc1.getId());
//				System.out.println(doc2.getId());
				int count = getSimilarityByCommonRating(ds,doc1, doc2);// �ҳ����Ƶĸ���
				doctorSimilarity[i][j] = count;
				doctorSimilarity[j][i] = count;// ��Ϊ�Ǹ��ԳƵ�
			}
		}
		return doctorSimilarity;
	}


	
	

}
