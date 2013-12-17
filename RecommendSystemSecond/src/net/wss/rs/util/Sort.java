package net.wss.rs.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.wss.rs.data.disease.DoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.DiseaseRatingEntity;


public class Sort {
	/**
	 * 对某一个医生的相似结果排序，按照降序的方式
	 * value接收这个医生的所有数据
	 * index接收下标，目的使排序前后的下标保持一致
	 * 将下标返回
	 * @param doctorSimilarity
	 * @return
	 */
	public static int[]  similaritySort(int[] doctorSimilarity){
		int[] value = doctorSimilarity;		
		int[] index = new int[value.length]; 
		for(int i=0;i < value.length;i++){
			index[i]=i;//先给下标赋值
		}
		
		for (int i = 0; i < value.length - 1; i++) {
			for (int j = i + 1; j < value.length; j++) {
				if (value[i] < value[j]) {// 大到小，小到大改>
					int tempc = value[i];
					value[i] = value[j];
					value[j] = tempc;

					int tempb = index[i];
					index[i] = index[j];
					index[j] = tempb;
				}
			}
		}
		return index;
	}
	public static int[]  similaritySortDouble(double[] doctorSimilarity){
		double[] value = doctorSimilarity;		
		int[] index = new int[value.length]; 
		for(int i=0;i < value.length;i++){
			index[i]=i;//先给下标赋值
		}
		
		for (int i = 0; i < value.length - 1; i++) {
			for (int j = i + 1; j < value.length; j++) {
				if (value[i] < value[j]) {// 大到小，小到大改>
					double tempc = value[i];
					value[i] = value[j];
					value[j] = tempc;

					int tempb = index[i];
					index[i] = index[j];
					index[j] = tempb;
				}
			}
		}
		return index;
	}
	
//	public static int[]  similaritySort(List<Integer> ratingList){
//		int[] value = doctorSimilarity;		
//		int[] index = new int[value.length]; 
//		for(int i=0;i < value.length;i++){
//			index[i]=i;//先给下标赋值
//		}
//		
//		for (int i = 0; i < value.length - 1; i++) {
//			for (int j = i + 1; j < value.length; j++) {
//				if (value[i] < value[j]) {// 大到小，小到大改>
//					int tempc = value[i];
//					value[i] = value[j];
//					value[j] = tempc;
//
//					int tempb = index[i];
//					index[i] = index[j];
//					index[j] = tempb;
//				}
//			}
//		}
//		return index;
//	}
	
}
