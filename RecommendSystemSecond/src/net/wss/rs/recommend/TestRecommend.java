package net.wss.rs.recommend;

import net.wss.rs.data.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.util.Sort;

public class TestRecommend {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DoctorRecommendDataset ds = new DoctorRecommendDataset();
		System.out.println("打印所有的评分");
		ds.printAllRating();
		System.out.println("打印所有的评分矩阵");
		ds.printAllRatingMatrix();
		RecommendDoctor re =new RecommendDoctor(ds);
		
		DoctorEntity d1 = new DoctorEntity();
		d1.setId(1);
		DoctorEntity d2 = new DoctorEntity();
		d2.setId(2);
		
		int count = re.getSimilarityByCommonRating(d1, d2);
		System.out.println("医生"+d1.getId()+"与医生"+d2.getId()+"相似度"+count);
		
		int count1 = re.getSimilarityBySumCommonRating(d1, d2);
		System.out.println("医生"+d1.getId()+"与医生"+d2.getId()+"相似度"+count1);
		
		System.out.println("两个医生共同看的病的个数，对角线是这个医生一共看过多少病：");
		int[][] allDoctorSimilarity = re.getAllDoctorSimilarity(0);
		int[] doctor2Sim = allDoctorSimilarity[2];//存入的是count
		int[] doctorSimIndexAsc=Sort.similaritySort(doctor2Sim);//存下标索引即用户的id
		
		for(int i=0; i< doctorSimIndexAsc.length;i++){
			System.out.print(doctorSimIndexAsc[i] + " ");
		}
		System.out.println();
		
		int kSim =3+1;
		for(int i=0; i<kSim ;i++){
			System.out.print(doctorSimIndexAsc[i] + " ");
		}
		System.out.println();
		
		
	}
}
