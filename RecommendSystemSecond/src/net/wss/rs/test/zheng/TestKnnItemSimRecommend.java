package net.wss.rs.test.zheng;

import java.util.HashMap;
import java.util.Map.Entry;

import net.wss.rs.data.zheng.ZhengDataSetConfig;
import net.wss.rs.data.zheng.ZhengDealDataSet;
import net.wss.rs.data.zheng.ZhengDoctorRecommendDataset;
import net.wss.rs.recommend.Recommender;
import net.wss.rs.similarity.zheng.KnnItemSimilarity;

public class TestKnnItemSimRecommend {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		计算关系矩阵的疾病数量
		int zhengCalCount = 30;
//		推荐的相似医生数量
		int recommendDocNum = 10;
//		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset();
		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset(ZhengDataSetConfig.AllDoctorPath,ZhengDataSetConfig.AllZhengPath,ZhengDataSetConfig.AllRatingPath);
//		处理数据源
		ZhengDealDataSet dds = new ZhengDealDataSet(ds);
//		给ds中的sortedRatingsByDoctorId赋值
		dds.setAllRatingSort(zhengCalCount);
		
//		计算doc-doc相似矩阵
		KnnItemSimilarity knnsim = new KnnItemSimilarity();
		int[][] doctorSimilarity = knnsim.getAllSimilarityByCommonRating(ds);
		
//		根据相似度推荐
		Recommender recom = new Recommender();
		HashMap<Integer,String> sortedAllDocSim = recom.recommendSimDoc(doctorSimilarity, recommendDocNum);
		
		for (Entry<Integer, String> entry: sortedAllDocSim.entrySet()) {
			System.out.println("doc id="+entry.getKey()+"  "+entry.getValue());
		}
	}

}
