package net.wss.rs.test.disease;

import java.util.HashMap;
import java.util.Map.Entry;

import net.wss.rs.data.disease.DataSetConfig;
import net.wss.rs.data.disease.DealDataSet;
import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.recommend.Recommender;
import net.wss.rs.similarity.disease.EucliddistanceItemSimilarity;


public class TestEucliddistanceItemSimilarity {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		计算关系矩阵的疾病数量。这个值需要经过多次测试，看哪个值得结果的精确度最高
		int diseaseCalCount = 13;
//		推荐的相似医生数量
		int recommendDocNum = 10;
//		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		DoctorRecommendDataset ds = new DoctorRecommendDataset(DataSetConfig.AllDoctorPath,DataSetConfig.AllDiseasePath,DataSetConfig.AllRatingPath);
//		处理数据源
		DealDataSet dds = new DealDataSet(ds);
//		给ds中的sortedRatingsByDoctorId赋值
		dds.setAllRatingSort(diseaseCalCount);
		
//		计算doc-doc相似矩阵
		EucliddistanceItemSimilarity Eucliddistancesim = new EucliddistanceItemSimilarity();
		double[][] doctorSimilarity = Eucliddistancesim.getAllDoctorSimilarity(ds);
		
//		根据相似度推荐
		Recommender recom = new Recommender();
		HashMap<Integer,String> sortedAllDocSim = recom.recommendSimDoc(doctorSimilarity, recommendDocNum);
		
		for (Entry<Integer, String> entry: sortedAllDocSim.entrySet()) {
			System.out.println("doc id="+entry.getKey()+"  "+entry.getValue());
		}
	}

}
