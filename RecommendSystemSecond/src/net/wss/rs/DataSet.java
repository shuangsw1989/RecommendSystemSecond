package net.wss.rs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.wss.rs.entity.DiseaseEntity;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.RatingEntity;

public class DataSet {
	/*
	 * 所有疾病的诊次
	 */
	private List<RatingEntity> allRating = new ArrayList<RatingEntity>();

	/*
	 * 得到所有的医生.
	 */
	private Map<Integer, DoctorEntity> allDoctor = new HashMap<Integer, DoctorEntity>();

	/*
	 * 得到所有的疾病
	 */
	private Map<Integer, DiseaseEntity> allDisease = new HashMap<Integer, DiseaseEntity>();

	/*
	 *通过疾病的id得到所有疾病的诊次
	 * 
	 */
	private Map<Integer, List<RatingEntity>> ratingsByDiseaseId = new HashMap<Integer, List<RatingEntity>>();
/*
 * 获取某个用户对某个疾病的诊断次数
 */
	private Map<Integer,Integer> ratingByDiseaseIdDocId = new HashMap<Integer,Integer>();
	/*
	 *
	 * 通过医生的id得到所有疾病的诊次
	 */
	Map<Integer, List<RatingEntity>> ratingsByDoctorId = new HashMap<Integer, List<RatingEntity>>();
	/*
	 * 某个医生说治疗的疾病的集合
	 */
	private Map<Integer, List<Integer>> diseaseByDoctorId = new HashMap<Integer, List<Integer>>();

	
	
	
	public Map<Integer, Integer> getRatingByDiseaseIdDocId() {
		return ratingByDiseaseIdDocId;
	}

	public void setRatingByDiseaseIdDocId(
			Map<Integer, Integer> ratingByDiseaseIdDocId) {
		this.ratingByDiseaseIdDocId = ratingByDiseaseIdDocId;
	}

	public List<RatingEntity> getAllRating() {
		return allRating;
	}

	public void setAllRating(List<RatingEntity> allRating) {
		this.allRating = allRating;
	}

	public Map<Integer, DoctorEntity> getAllDoctor() {
		return allDoctor;
	}

	public void setAllDoctor(Map<Integer, DoctorEntity> allDoctor) {
		this.allDoctor = allDoctor;
	}

	public Map<Integer, DiseaseEntity> getAllDisease() {
		return allDisease;
	}

	public void setAllDisease(Map<Integer, DiseaseEntity> allDisease) {
		this.allDisease = allDisease;
	}

	public Map<Integer, List<RatingEntity>> getRatingsByDiseaseId() {
		return ratingsByDiseaseId;
	}

	public void setRatingsByDiseaseId(
			Map<Integer, List<RatingEntity>> ratingsByDiseaseId) {
		this.ratingsByDiseaseId = ratingsByDiseaseId;
	}

	public Map<Integer, List<RatingEntity>> getRatingsByDoctorId() {
		return ratingsByDoctorId;
	}

	public void setRatingsByDoctorId(
			Map<Integer, List<RatingEntity>> ratingsByDoctorId) {
		this.ratingsByDoctorId = ratingsByDoctorId;
	}

	public Map<Integer, List<Integer>> getDiseaseByDoctorId() {
		return diseaseByDoctorId;
	}

	public void setDiseaseByDoctorId(Map<Integer, List<Integer>> diseaseByDoctorId) {
		this.diseaseByDoctorId = diseaseByDoctorId;
	}


	
	
	/**
	 * 判断专家和疾病是否存在关系
	 * 
	 * @param docId
	 * @param disId
	 * @return
	 */
	public boolean isExist(int docId, int disId) {
		List<Integer> disList = diseaseByDoctorId.get(docId);// 定义一个集合，存放一个医生所治的所有疾病

		if (disList == null) {// 当集合为空的时候，返回false
			return false;
		}

		for (int tempDisId : disList) {
			if (tempDisId == disId) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 打印所有医生的key-value,key,value是医生的id
	 */
	public void printAllDoctor() {

		for (Entry<Integer, DoctorEntity> entry: allDoctor.entrySet()) {
			Integer key = entry.getKey();
			DoctorEntity  value = entry.getValue();
			
			System.out.println(value.getId());
		}
	}
	/**
	 * 打印所有疾病key-value，key,value是疾病的id
	 */
	public void printAllDisease() {

		for (Entry<Integer, DiseaseEntity> entry: allDisease.entrySet()) {
			Integer key = entry.getKey();
			DiseaseEntity  value = entry.getValue();
			
			System.out.println(value.getId());
		}
	}
	/**
	 * 打印所有诊次key-value，key,value是疾病的id
	 */
	public void printAllRating() {

		for (Entry<Integer, RatingEntity> entry: allRating.entrySet()) {
			Integer key = entry.getKey();
			DiseaseEntity  value = entry.getValue();
			
			System.out.println(value.getId());
		}
	}

	/**
	 * 打印出所有的医生及其所治疗的疾病（矩阵的形式显示） 
	 * 先判断是否存在关系 
	 * 如果医生治疗某个疾病，则将其置为1 
	 * 如果医生不治疗某个疾病，则将其置为0
	 */
//	public void print() {
//		for (DoctorEntity de : allDoctor) {
//			for (DiseaseEntity dis : allDisease) {
//				int docId = de.getId();// 得到医生的id
//				int disId = dis.getId();// 得到疾病的id
//				if (isExist(docId, disId)) {// 判断医生和疾病是否有关系
//					System.out.print("1" + " ");// 有关系输出1
//				} else {
//					System.out.print("0" + " ");// 没有关系输出0
//				}
//
//			}
//			System.out.println();
//		}
//	}

}
