package net.wss.rs.evaluation;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import net.wss.rs.data.disease.DataSetConfig;
import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.DiseaseRatingEntity;
import net.wss.rs.util.FileUtil;

public class DoctorEvaluation {
	DoctorRecommendDataset ds;

	public DoctorEvaluation(DoctorRecommendDataset ds) {
		this.ds = ds;
	}
	/**
	 * 计算两个用户的相似度
	 * 计算的方法是欧式距离的方法
	 * @param doctor1
	 * @param doctor2
	 * @return
	 */
	
	public double getSimilarityByEucliddistance(DoctorEntity doctor1,
			DoctorEntity doctor2) {
		List<DiseaseRatingEntity> dis1 = ds.getRatingsByDoctorId()
				.get(doctor1.getId());// 获取某一个医生所包含的疾病
		List<DiseaseRatingEntity> dis2 = ds.getRatingsByDoctorId()
				.get(doctor2.getId());
		
		int commDiseases=0;
		if (dis1 == null || dis2 == null) {
//			System.err.println("医生没有治过病");
			return 0;
		}
		
		
//		for (int i = 0; i < dis1.size(); i++) {
//			System.out.print(dis1.get(i).getDiseaseId()+":"+dis1.get(i).getRating()+" ");			
//		}
//		System.out.println();
//		for (int i = 0; i < dis2.size(); i++) {
//			System.out.print(dis2.get(i).getDiseaseId()+":"+dis2.get(i).getRating()+" ");			
//		}
//		System.out.println();
//		

		// 判断两个疾病集合是否有交集
		double sim=0.0;
		
		for (int i = 0; i < dis1.size(); i++) {
			for (int j = 0; j < dis2.size(); j++) {
				if (dis1.get(i).getDiseaseId() == dis2.get(j).getDiseaseId()) {
					commDiseases++;
					int dis1Rating = dis1.get(i).getRating();
					int dis2Rating = dis2.get(j).getRating();
//					System.out.println("common dis id:"+dis1.get(i).getDiseaseId()+" "+dis1Rating+" "+dis2Rating);
					sim += Math.pow((dis1Rating-dis2Rating),2);
				}
			}
		}
//		System.out.println("欧式距离相似度："+sim);
		if(commDiseases>0){
//			sim=1.0d - Math.tanh(sim);
//			sim=1.0/(sim+1);
			sim=sim;
		}
		
		return sim;
	}
	/**
	 * 计算所有医生的相似度
	 * @type
	 * 0表示利用的是欧式距离
	 * 1表示利用的是雅克比
	 */
	
	public double[][] getAllDoctorSimilarity(int type) {
		int allDoctorSize=ds.getAllDoctor().size();
//		System.out.println("allDoctorSize"+allDoctorSize);
		
		double[][] doctorSimilarity = new double[allDoctorSize+1][allDoctorSize+1];
	
		for (Entry<Integer, DoctorEntity> entry: ds.getAllDoctor().entrySet()) {
			DoctorEntity doc1 = entry.getValue();
			int i= doc1.getId();
			for (Entry<Integer, DoctorEntity> entry2: ds.getAllDoctor().entrySet()) {
				
				DoctorEntity doc2 = entry2.getValue();
				int j= doc2.getId();
				if (doc2.getId() > doc1.getId()) {
					continue;// 跳出本次循环，执行下次循环
				}
//				System.out.println(doc1.getId());
//				System.out.println(doc2.getId());
				double count=0;
				if(type==0){
					count = getSimilarityByEucliddistance(doc1, doc2);// 找出相似的个数
					doctorSimilarity[i][j] = count;
					doctorSimilarity[j][i] = count;// 因为是个对称的
				}else if(type==1){
					count = getSimilarityByJacobi(doc1, doc2);// 找出相似的个数
					doctorSimilarity[i][j] = count;
					doctorSimilarity[j][i] = count;// 因为是个对称的
				}
			}
		}
		
		File file = new File(DataSetConfig.SimiResultPath);
		if (file.exists()) {		
			file.delete();
		} 
		
		for (int i = 0; i < doctorSimilarity.length; i++) {
			String line="";
			for (int j = 0; j < doctorSimilarity[i].length; j++) {
				line +=String.format("%.4f",doctorSimilarity[i][j]) + " ";
//				System.out.print(String.format("%.4f",doctorSimilarity[i][j]) + " ");
			}
			System.out.println();
			FileUtil.appendData(DataSetConfig.SimiResultPath, line);
		}
	
		return doctorSimilarity;
	}
	/**
	 * 计算两个用户的相似度
	 * 计算方法是雅克比距离
	 * @param doctor1
	 * @param doctor2
	 * @return
	 */
	public double getSimilarityByJacobi(DoctorEntity doctor1,
			DoctorEntity doctor2) {
		List<DiseaseRatingEntity> dis1 = ds.getRatingsByDoctorId()
				.get(doctor1.getId());// 获取某一个医生所包含的疾病
		List<DiseaseRatingEntity> dis2 = ds.getRatingsByDoctorId()
				.get(doctor2.getId());
		
		int commDiseases=0;
		if (dis1 == null || dis2 == null) {
//			System.err.println("医生没有治过病");
			return 0;
		}
		// 判断两个疾病集合是否有交集
		double sim=0.0d;
		for (int i = 0; i < dis1.size(); i++) {
			for (int j = 0; j < dis2.size(); j++) {
				if (dis1.get(i).getDiseaseId() == dis2.get(j).getDiseaseId()) {
					commDiseases++;
				}
			}
		}
//		System.out.println("两个医生治疗相同疾病的个数："+commDiseases);
//		System.out.println("第一个医生所治疾病的个数："+dis1.size());	
//		System.out.println("第二个医生所治疾病的个数："+dis2.size());
		sim = commDiseases*1.0/(dis1.size()+dis2.size()-commDiseases);
//		System.out.println("雅克比相似度："+String.format("%.5f",sim));	
		return sim;
	}
}
