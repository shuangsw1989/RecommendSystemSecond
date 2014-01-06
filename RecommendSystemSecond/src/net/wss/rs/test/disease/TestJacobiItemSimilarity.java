package net.wss.rs.test.disease;

import java.util.HashMap;
import java.util.Map.Entry;

import net.wss.rs.data.disease.DataSetConfig;
import net.wss.rs.data.disease.DealDataSet;
import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.recommend.Recommender;
import net.wss.rs.similarity.disease.JacobiItemSimilarity;

public class TestJacobiItemSimilarity {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		�����ϵ����ļ�������
		int diseaseCalCount = 13;
//		�Ƽ�������ҽ������
		int recommendDocNum = 10;
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
//		��������Դ
		DealDataSet dds = new DealDataSet(ds);
//		��ds�е�sortedRatingsByDoctorId��ֵ
		dds.setAllRatingSort(diseaseCalCount);
		
//		����doc-doc���ƾ���
		JacobiItemSimilarity jacobisim = new JacobiItemSimilarity();
		double[][] doctorSimilarity = jacobisim.getAllDoctorSimilarity(ds);
		
//		�������ƶ��Ƽ�
		Recommender recom = new Recommender();
		HashMap<Integer,String> sortedAllDocSim = recom.recommendSimDoc(doctorSimilarity, recommendDocNum);
		
		for (Entry<Integer, String> entry: sortedAllDocSim.entrySet()) {
			System.out.println("doc id="+entry.getKey()+"  "+entry.getValue());
		}

	}

}
