package net.wss.rs.recommend;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.DiseaseRatingEntity;
import net.wss.rs.util.Sort;

public class DiseaseKNNSimilarity {

	
	DoctorRecommendDataset ds;

	public DiseaseKNNSimilarity(DoctorRecommendDataset ds) {
		this.ds = ds;
	}
	/**
	 * ��һ��ҽ�����μ����������ֽ�������,ȡ��ǰk��
	 * 
	 * k = 0 ȫ��
	 * k > 0 ȡǰK��
	 * k < 0 ��
	 * @param doctor1
	 * @return
	 */
	public List<DiseaseRatingEntity> ratingSortByDocId(int docId,int k){
		if(k < 0){
			return new ArrayList<DiseaseRatingEntity>();
		}
		
		List<DiseaseRatingEntity> docRatingList =ds.ratingsByDoctorId.get(docId);
		if(docRatingList==null){
			System.out.println("doctor:"+docId+"û�п���");
			return new ArrayList<DiseaseRatingEntity>();
		}
		//��doc���Ĳ�list����ת����array
//		RatingEntity[] docRatingArray = new  RatingEntity[docRatingList.size()];
//		docRatingList.toArray(docRatingArray);		
		//ȡ��ҽ�������֣���������
//		int[] docRatingValueArray = new int[docRatingArray.length];
//		for (int i=0; i<docRatingArray.length;i++) {
//			docRatingValueArray[i] = docRatingArray[i].getRating();
//		}

		//ȡ��ҽ�������֣���������
		int[] docRatingValueArray = new int[docRatingList.size()];
		for (int i=0; i<docRatingValueArray.length;i++) {
			docRatingValueArray[i] = docRatingList.get(i).getRating();
		}
		//���������򣬷�������
		int[] docRatingIndexArray  = Sort.similaritySort(docRatingValueArray);
		
		//�����±���list���ҳ���Ӧ��ʵ��
		List<DiseaseRatingEntity> sortedDocRatingList =new ArrayList<DiseaseRatingEntity>();
		int listLen = 0;
		if(k > 0 && docRatingIndexArray.length > k){
			listLen = k;
		}else{
			listLen = docRatingIndexArray.length;
		}
//		System.out.println(listLen);
		for (int i = 0; i < listLen; i++) {
			int docIdIndex = docRatingIndexArray[i];
			DiseaseRatingEntity sortedRating = docRatingList.get(docIdIndex);
			sortedDocRatingList.add(sortedRating);	
		}
		
//		for (int i = 0; i < sortedDocRatingList.size(); i++) {		
//			System.out.print(sortedDocRatingList.get(i).getDiseaseId()+":"+sortedDocRatingList.get(i).getRating() + " " );
//		}		
//		System.out.println();
		
		return sortedDocRatingList;
	}
	
	
	/**
	 * ������ҽ���������ֽ�������
	 * ��һ�ַ������޷���ֵ
	 * @param k  ȡǰk��
	 * @return
	 */
	public void setAllRatingSort(int k){
//		Map<Integer, List<RatingEntity>> sortedRatingsByDoctorId;
		
		for (Entry<Integer, DoctorEntity> entry: ds.allDoctor.entrySet()) {
			Integer docKey = entry.getKey();//��ȡһ��ҽ����id
//			System.out.print( "doc id:"+docKey+"  ");
			 ds.sortedRatingsByDoctorId.put(docKey, ratingSortByDocId(docKey,k));
		}		
		
	}
	/**
	 * ������ҽ���������ֽ������У�ȡ��ǰk������
	 * �ڶ��ַ������з���ֵ
	 * @param k
	 * @return
	 */
	public Map<Integer, List<DiseaseRatingEntity>> allRatingSort(int k){
		Map<Integer, List<DiseaseRatingEntity>> sortedRatingsByDoctorId = new HashMap<Integer, List<DiseaseRatingEntity>>();
		
		for (Entry<Integer, DoctorEntity> entry: ds.allDoctor.entrySet()) {
			Integer docKey = entry.getKey();//��ȡһ��ҽ����id
//			System.out.print( "doc id:"+docKey+"  ");
			sortedRatingsByDoctorId.put(docKey, ratingSortByDocId(docKey,k));
		}
		
		return sortedRatingsByDoctorId;
	}
	
	/**
	 * ��������ҽ�������ƶ� ������Σ�ֻ���㹲ͬ���Ĳ��ĸ���
	 */
	public int getSimilarityByCommonRating(DoctorEntity doctor1,
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
					continue;// ��������ѭ����ִ���´�ѭ��
				}
//				System.out.println(doc1.getId());
//				System.out.println(doc2.getId());
				int count = getSimilarityByCommonRating(doc1, doc2);// �ҳ����Ƶĸ���
				doctorSimilarity[i][j] = count;
				doctorSimilarity[j][i] = count;// ��Ϊ�Ǹ��ԳƵ�
			}
		}
		return doctorSimilarity;
	}

/**
 * ��������Ƶľ��󣬸������ƶ�����ȡ��recommendDocNum��ҽ��������sortedAllDocSim�У��������Ƽ�
 * @param doctorSimilarity ҽ��֮������ƶȾ���
 * @param recommendDocNum,�Ƽ�������
 * @return
 */
	public HashMap<Integer,String> recommendSimDoc(int[][] doctorSimilarity,int recommendDocNum) {
		HashMap<Integer,String> sortedAllDocSim = new HashMap<Integer,String>();
		for (int i = 1; i < doctorSimilarity.length; i++) {
			int[] doctorSim = doctorSimilarity[i];//ȡ��һ��ҽ��������ҽ�������ƶ�
			// ���ƶ�����
			int[] sortedDocSim = Sort.similaritySort(doctorSim);

			String docSimStr = "";
//			System.out.print("doc id:" + i + " ");
			int count = 0;
			int temp = 0;
			while (count < recommendDocNum && temp < sortedDocSim.length) {

				if (sortedDocSim[temp] == 0 || sortedDocSim[temp] == i || doctorSim[temp] <= 0) {//ҽ��û�п��������
					temp++;
					continue;
				}
				//ҽ�����������ҽ�������ǰrecommendDocNum����ƴ�ӳ�Ϊһ���ַ���
				docSimStr += sortedDocSim[temp] + ",";
				// System.out.print(sortedDocSim[temp]+" ");
				temp++;
				count++;
			}
			if(docSimStr.length() >0){//ȥ���ַ�������һ������
				docSimStr = docSimStr.substring(0, docSimStr.length() - 1);
			}
			
			// System.out.println();
//			System.out.println(docSimStr);
			sortedAllDocSim.put(i, docSimStr);//��ҽ��id���Ƽ����ַ���ֵ��д�뵽map��
		}
		return sortedAllDocSim;
	}
	
	
	/**
	 * ��ÿ��ҽ���Լ��������ְ��ս�������
	 * ȡ��ǰ5��
	 */
/*	public void ratingSort(int k){
		
		for (Entry<Integer, DoctorEntity> entry: ds.allDoctor.entrySet()) {
			Integer docKey = entry.getKey();//��ȡһ��ҽ����id
			System.out.print( "doc id:"+docKey+"  ");
			List<RatingEntity> docRatingList =ds.ratingsByDoctorId.get(docKey);
			//����id�õ�һ��ҽ�������м�������εļ���
//			int[] docRatingIndexArray = ratingSortByDocId(docKey);
			
			
			
//			ת����array
//			if(docRatingList.size()){
//				
//			}
//			Integer[] docRatingArray = new Integer[];
//			for()
			if(docRatingList == null){
				System.out.println();
				continue;
			}
			//���������ת�����������ʽ
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
				System.out.print("������id:"+docRatingList.get(docRatingIndexArray[i]).getDiseaseId()+"  ");
			}
			System.out.println();
		}
		
	}*/
}
