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
	 * ��һ��ҽ������֢״�������ֽ�������,ȡ��ǰk��
	 * 
	 * k = 0 ȫ��
	 * k > 0 ȡǰK��
	 * k < 0 ��
	 * @param doctor1
	 * @return
	 */
	public List<ZhengRatingEntity> ratingSortByDocId(int docId,int k){
		if(k < 0){
			return new ArrayList<ZhengRatingEntity>();
		}
		
		List<ZhengRatingEntity> docRatingList =ds.ratingsByDoctorId.get(docId);
		if(docRatingList==null){
			System.out.println("doctor:"+docId+"û�п���");
			return new ArrayList<ZhengRatingEntity>();
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
	 * ������ҽ���������ֽ������У�ȡ��ǰk��֢״
	 * �ڶ��ַ������з���ֵ
	 * @param k
	 * @return
	 */
	public Map<Integer, List<ZhengRatingEntity>> allRatingSort(int k){
		Map<Integer, List<ZhengRatingEntity>> sortedRatingsByDoctorId = new HashMap<Integer, List<ZhengRatingEntity>>();
		
		for (Entry<Integer, DoctorEntity> entry: ds.allDoctor.entrySet()) {
			Integer docKey = entry.getKey();//��ȡһ��ҽ����id
//			System.out.print( "doc id:"+docKey+"  ");
			sortedRatingsByDoctorId.put(docKey, ratingSortByDocId(docKey,k));
		}
		
		return sortedRatingsByDoctorId;
	}
	
}
