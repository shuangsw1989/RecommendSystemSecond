package net.wss.rs.evaluation;

import java.util.List;
import java.util.Map.Entry;

import net.wss.rs.data.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.RatingEntity;

public class DoctorEvaluation {
	DoctorRecommendDataset ds;

	public DoctorEvaluation(DoctorRecommendDataset ds) {
		this.ds = ds;
	}
//	/**
//	 * 计算两个的差值
//	 * @param a
//	 * @param b
//	 * @return
//	 */
//	public int calculatePoor(int a,int b){
//		int poor=0;
//		poor=a-b;
//		return poor;
//	}
//	/**
//	 * 计算差值的平方
//	 * @param poor
//	 * @return
//	 */
//	public int calculateSquare(int poor){
//		int square =0;
//		square=poor*poor;
//		return square;
//	}
//	/**
//	 * 计算平方和
//	 * @param square
//	 * @return
//	 */
//	public int calculateSquareAnd(int square){
//		int squareAnd=0;
//		squareAnd=squareAnd+square;
//		return squareAnd;
//	}
//	/**
//	 * 对平方和求平方根
//	 * @param squareAnd
//	 * @return
//	 */
//	public double calculateSquareRoot(int squareAnd){
//		double squareAndRoot=0.0;
//		squareAndRoot=Math.sqrt(squareAnd);
//		return squareAndRoot;
//	}
//	/**
//	 * 取得计算后的结果
//	 * @param a
//	 * @param b
//	 * @return
//	 */
//	public double calculateResult(int a,int b){
//		double result =0.0;
//		int poor=calculatePoor(a,b);
//		int square=calculateSquare(poor);
//		int squareAnd=calculateSquareAnd(square);
//		result=calculateSquareRoot(squareAnd);
//		return result;
//	}
	/**
	 * 计算两个用户的相似度
	 * @param doctor1
	 * @param doctor2
	 * @return
	 */
	
	public double getSimilarityByCommonRating(DoctorEntity doctor1,
			DoctorEntity doctor2) {
		List<RatingEntity> dis1 = ds.getRatingsByDoctorId()
				.get(doctor1.getId());// 获取某一个医生所包含的疾病
		List<RatingEntity> dis2 = ds.getRatingsByDoctorId()
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
//		System.out.println(sim);
//		if(commDiseases>0){
//			sim=1.0d - Math.tanh(sim);
//		}
		
		return sim;
	}
	/**
	 * 计算所有医生的相似度
	 */
	public double[][] getAllDoctorSimilarity() {
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
				count = getSimilarityByCommonRating(doc1, doc2);// 找出相似的个数
				doctorSimilarity[i][j] = count;
				doctorSimilarity[j][i] = count;// 因为是个对称的
			}
		}
		
		for (int i = 0; i < doctorSimilarity.length; i++) {
			for (int j = 0; j < doctorSimilarity[i].length; j++) {
				System.out.print(doctorSimilarity[i][j] + " ");
			}
			System.out.println();
		}
		
		return doctorSimilarity;
	}
}
