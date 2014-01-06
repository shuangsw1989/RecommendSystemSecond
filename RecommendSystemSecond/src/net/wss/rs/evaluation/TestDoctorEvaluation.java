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
		testTwoDoctorEucliddistance();// ����ҽ�� ŷ�Ͼ���
		testTwoDoctorJacbi();// ����ҽ�� �ſ˱�
		testAllDoctorDistance();// ����ҽ�� ŷ�Ͼ��� �ſ˱���type����

	}

	public static void testAllDoctorDistance() {

		// DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(
				DataSetConfig.AllDoctorPath, DataSetConfig.AllDiseasePath,
				DataSetConfig.AllRatingPath);
		DoctorEvaluation de = new DoctorEvaluation(ds);

		// 0��ʾ����ŷʽ���뷽��
		// 1��ʾ�����ſ˱�
		double[][] allDoctorSimilarity = de.getAllDoctorSimilarity(0);

		for (int docId = 1; docId < allDoctorSimilarity.length; docId++) {
			double[] doctorSim = allDoctorSimilarity[docId];// �������count
			int[] doctorSimIndexAsc = Sort.similaritySortDouble(doctorSim);// ���±��������û���id

			System.out.println("��ҽ��" + docId + "���ƵĽ������������");
			for (int i = 0; i < doctorSimIndexAsc.length; i++) {
				System.out.print(doctorSimIndexAsc[i] + " ");
			}
			System.out.println();
			System.out.println("��ҽ��" + docId + "�����Ƶ�ǰ"
					+ Constant.biggestratingnum + "��ҽ��");
			
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
		double sim = de.getSimilarityByEucliddistance(d1, d2);// ŷ�Ͼ���
		System.out.println("ŷʽ�����������ƶ�Ϊ��" + sim);
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
		double sim = de.getSimilarityByJacobi(d1, d2);// �ſ˱�
		System.out.println("�ſ˱ȼ�������ƶ�Ϊ��" + sim);
	}
}
