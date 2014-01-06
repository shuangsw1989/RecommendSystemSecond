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
//		计算关系矩阵的疾病数量
		int diseaseCalCount = 30;
//		推荐的相似医生数量
		int recommendDocNum = 10;
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
		
		DiseaseKNNSimilarity knn = new DiseaseKNNSimilarity(ds);
//		给ds中的sortedRatingsByDoctorId赋值
		knn.setAllRatingSort(diseaseCalCount);
		
		int[][] doctorSimilarity = knn.getAllSimilarityByCommonRating();
		
		HashMap<Integer,String> sortedAllDocSim = knn.recommendSimDoc(doctorSimilarity, recommendDocNum);
		KbDoctorService kbservice = new KbDoctorService();
		for (Entry<Integer, String> entry: sortedAllDocSim.entrySet()) {
//			kbservice.updateDocSim(entry.getKey(), entry.getValue());//更新数据库
			String line = entry.getKey()+DataSetConfig.AttrSplit+entry.getValue();
			FileUtil.appendData(DataSetConfig.RecommendResultPath, line);//写文件
//			System.out.println("doc id="+entry.getKey()+"  "+entry.getValue());
		}
	}

}
