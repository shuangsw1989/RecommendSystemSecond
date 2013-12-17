package net.wss.rs.evaluation;

import net.wss.rs.confi.Constant;
import net.wss.rs.data.disease.DataSetConfig;
import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.util.Sort;

public class TestDoctorEvaluation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testTwoDoctorEucliddistance();// 两个医生 欧氏距离
		testTwoDoctorJacbi();// 两个医生 雅克比
		testAllDoctorDistance();// 所有医生 欧氏距离 雅克比用type控制

	}

	public static void testAllDoctorDistance() {

		// DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(
				DataSetConfig.AllDoctorPath, DataSetConfig.AllDiseasePath,
				DataSetConfig.AllRatingPath);
		DoctorEvaluation de = new DoctorEvaluation(ds);

		// 0表示利用欧式距离方法
		// 1表示利用雅克比
		double[][] allDoctorSimilarity = de.getAllDoctorSimilarity(0);

		for (int docId = 1; docId < allDoctorSimilarity.length; docId++) {
			double[] doctorSim = allDoctorSimilarity[docId];// 存入的是count
			int[] doctorSimIndexAsc = Sort.similaritySortDouble(doctorSim);// 存下标索引即用户的id

			System.out.println("与医生" + docId + "相似的结果，降序排列");
			for (int i = 0; i < doctorSimIndexAsc.length; i++) {
				System.out.print(doctorSimIndexAsc[i] + " ");
			}
			System.out.println();
			System.out.println("与医生" + docId + "最相似得前"
					+ Constant.biggestratingnum + "个医生");
			
			for (int i = 0; i < Constant.biggestratingnum; i++) {

				System.out.print(doctorSimIndexAsc[i] + " ");
				
			}
			System.out.println();
		}
	}

	public static void testTwoDoctorEucliddistance() {
		// DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(
				DataSetConfig.AllDoctorPath, DataSetConfig.AllDiseasePath,
				DataSetConfig.AllRatingPath);
		DoctorEvaluation de = new DoctorEvaluation(ds);

		DoctorEntity d1 = new DoctorEntity();
		d1.setId(2);
		DoctorEntity d2 = new DoctorEntity();
		d2.setId(3);
		double sim = de.getSimilarityByEucliddistance(d1, d2);// 欧氏距离
		System.out.println("欧式距离计算的相似度为：" + sim);
	}

	public static void testTwoDoctorJacbi() {
		// DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(
				DataSetConfig.AllDoctorPath, DataSetConfig.AllDiseasePath,
				DataSetConfig.AllRatingPath);
		DoctorEvaluation de = new DoctorEvaluation(ds);

		DoctorEntity d1 = new DoctorEntity();
		d1.setId(2);
		DoctorEntity d2 = new DoctorEntity();
		d2.setId(40);
		double sim = de.getSimilarityByJacobi(d1, d2);// 雅克比
		System.out.println("雅克比计算的相似度为：" + sim);
	}
}
