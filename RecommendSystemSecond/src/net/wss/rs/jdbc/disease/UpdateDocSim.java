package net.wss.rs.jdbc.disease;

import java.util.HashMap;
import java.util.Map.Entry;

import net.wss.rs.data.disease.DataSetConfig;
import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.recommend.DiseaseKNNSimilarity;
import net.wss.rs.util.FileUtil;

public class UpdateDocSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		�����ϵ����ļ�������
		int diseaseCalCount = 30;
//		�Ƽ�������ҽ������
		int recommendDocNum = 10;
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
//		��ds�е�sortedRatingsByDoctorId��ֵ
		knn.setAllRatingSort(diseaseCalCount);
		
		int[][] doctorSimilarity = knn.getAllSimilarityByCommonRating();
		
		HashMap<Integer,String> sortedAllDocSim = knn.recommendSimDoc(doctorSimilarity, recommendDocNum);
		KbDoctorService kbservice = new KbDoctorService();
		for (Entry<Integer, String> entry: sortedAllDocSim.entrySet()) {
//			kbservice.updateDocSim(entry.getKey(), entry.getValue());//�������ݿ�
			String line = entry.getKey()+DataSetConfig.AttrSplit+entry.getValue();
			FileUtil.appendData(DataSetConfig.RecommendResultPath, line);//д�ļ�
//			System.out.println("doc id="+entry.getKey()+"  "+entry.getValue());
		}
	}

}
