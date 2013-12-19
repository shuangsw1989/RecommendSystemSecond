package net.wss.rs.jdbc.zheng;

import java.util.HashMap;
import java.util.Map.Entry;

import net.wss.rs.data.disease.DataSetConfig;
import net.wss.rs.data.zheng.ZhengDataSetConfig;
import net.wss.rs.data.zheng.ZhengDealDataSet;
import net.wss.rs.data.zheng.ZhengDoctorRecommendDataset;
import net.wss.rs.recommend.ZhengRecommend;
import net.wss.rs.similarity.zheng.ZhengKnnItemSimilarity;
import net.wss.rs.util.FileUtil;

public class UpdateDocSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		计算关系矩阵的疾病数量
		int zhengCalCount = 10;
//		推荐的相似医生数量
		int recommendDocNum = 10;
//		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset();
		ZhengDoctorRecommendDataset ds = new ZhengDoctorRecommendDataset(ZhengDataSetConfig.AllDoctorPath,ZhengDataSetConfig.AllZhengPath,ZhengDataSetConfig.AllRatingPath);
//		处理数据源
		ZhengDealDataSet dds = new ZhengDealDataSet(ds);
//		给ds中的sortedRatingsByDoctorId赋值
		dds.setAllRatingSort(zhengCalCount);
		
//		计算doc-doc相似矩阵
		ZhengKnnItemSimilarity knnsim = new ZhengKnnItemSimilarity();
		int[][] doctorSimilarity = knnsim.getAllSimilarityByCommonRating(ds);
		
//		根据相似度推荐
		ZhengRecommend recom = new ZhengRecommend();
		HashMap<Integer,String> sortedAllDocSim = recom.recommendSimDoc(doctorSimilarity, recommendDocNum);
		KbDoctorService kbservice = new KbDoctorService();
		for (Entry<Integer, String> entry: sortedAllDocSim.entrySet()) {
//			kbservice.updateDocSim(entry.getKey(), entry.getValue());//更新数据库
			String line = entry.getKey()+ZhengDataSetConfig.AttrSplit+entry.getValue();
			FileUtil.appendData(ZhengDataSetConfig.RecommendResultPath, line);//写文件
//			System.out.println("doc id="+entry.getKey()+"  "+entry.getValue());
		}
	}

}
