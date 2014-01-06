package net.wss.rs.similarity.disease;

import java.util.List;
import java.util.Map.Entry;

import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.DiseaseRatingEntity;


public class KnnItemSimilarity {
	
	
	/**
	 * ��������ҽ�������ƶ� ������Σ�ֻ���㹲ͬ���Ĳ��ĸ���
	 */
	public int getSimilarityByCommonRating(DoctorRecommendDataset ds ,DoctorEntity doctor1,
			DoctorEntity doctor2) {
//		System.out.println(doctor1.getId());
		List<DiseaseRatingEntity> dis1 = ds.getSortedRatingsByDoctorId()
				.get(doctor1.getId());// ��ȡĳһ��ҽ���������ļ���
				
		List<DiseaseRatingEntity> dis2 = ds.getSortedRatingsByDoctorId()
				.get(doctor2.getId());

		// �������������������һ��Ϊ�գ���˵������ҽ�������ƣ�����0
		int count = 0;
		if (dis1 == null || dis2 == null) {
			System.err.println("ҽ��û�п�����");
			return 0;
		}

		// �ж��������������Ƿ��н������������count+1
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
	 * ��������ҽ�������ƶ� ������Σ�ֻ���㹲ͬ���Ĳ��ĸ���
	 */
	public int[][] getAllSimilarityByCommonRating(DoctorRecommendDataset ds) {
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
