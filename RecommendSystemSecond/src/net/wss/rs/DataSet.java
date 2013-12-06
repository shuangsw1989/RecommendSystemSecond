package net.wss.rs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.wss.rs.entity.DiseaseEntity;
import net.wss.rs.entity.DoctorEntity;

public class DataSet {

	/*
	 * 所有疾病的集合.
	 */
	private List<DiseaseEntity> allDisease = new ArrayList<DiseaseEntity>();
	/*
	 * 所有专家的集合
	 */
	private List<DoctorEntity> allDoctor = new ArrayList<DoctorEntity>();
	/*
	 * 某个医生说治疗的疾病的集合
	 */
	private Map<Integer, List<Integer>> diseaseByDoctorId = new HashMap<Integer, List<Integer>>();

	public List<DiseaseEntity> getAllDisease() {
		return allDisease;
	}

	public void setAllDisease(List<DiseaseEntity> allDisease) {
		this.allDisease = allDisease;
	}

	public List<DoctorEntity> getAllDoctor() {
		return allDoctor;
	}

	public void setAllDoctor(List<DoctorEntity> allDoctor) {
		this.allDoctor = allDoctor;
	}

	public Map<Integer, List<Integer>> getDiseaseByDoctorId() {
		return diseaseByDoctorId;
	}

	public void setDiseaseByDoctorId(
			Map<Integer, List<Integer>> diseaseByDoctorId) {
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
	 * 打印出所有的医生及其所治疗的疾病（矩阵的形式显示） 
	 * 先判断是否存在关系 
	 * 如果医生治疗某个疾病，则将其置为1 
	 * 如果医生不治疗某个疾病，则将其置为0
	 */
	public void print() {
		for (DoctorEntity de : allDoctor) {
			for (DiseaseEntity dis : allDisease) {
				int docId = de.getId();// 得到医生的id
				int disId = dis.getId();// 得到疾病的id
				if (isExist(docId, disId)) {// 判断医生和疾病是否有关系
					System.out.print("1" + " ");// 有关系输出1
				} else {
					System.out.print("0" + " ");// 没有关系输出0
				}

			}
			System.out.println();
		}
	}

}
