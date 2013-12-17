package net.wss.rs.recommend;

import java.util.HashMap;

import net.wss.rs.util.Sort;

public class ZhengRecommend {
	/**
	 * 将获得相似的矩阵，根据相似度排序，取出recommendDocNum个医生，放入sortedAllDocSim中，并给予推荐
	 * @param doctorSimilarity 医生之间的相似度矩阵
	 * @param recommendDocNum,推荐的数量
	 * @return
	 */
		public HashMap<Integer,String> recommendSimDoc(int[][] doctorSimilarity,int recommendDocNum) {
			HashMap<Integer,String> sortedAllDocSim = new HashMap<Integer,String>();
			for (int i = 1; i < doctorSimilarity.length; i++) {
				int[] doctorSim = doctorSimilarity[i];//取出一个医生与所有医生的相似度
				// 相似度排序
				int[] sortedDocSim = Sort.similaritySort(doctorSim);

				String docSimStr = "";
//				System.out.print("doc id:" + i + " ");
				int count = 0;
				int temp = 0;
				while (count < recommendDocNum && temp < sortedDocSim.length) {

					if (sortedDocSim[temp] == 0 || sortedDocSim[temp] == i || doctorSim[temp] <= 0) {//医生没有看病的情况
						temp++;
						continue;
					}
					//医生看过病，且将排序后的前recommendDocNum个，拼接成为一个字符串
					docSimStr += sortedDocSim[temp] + ",";
					// System.out.print(sortedDocSim[temp]+" ");
					temp++;
					count++;
				}
				if(docSimStr.length() >0){//去掉字符串最后的一个逗号
					docSimStr = docSimStr.substring(0, docSimStr.length() - 1);
				}
				
				// System.out.println();
//				System.out.println(docSimStr);
				sortedAllDocSim.put(i, docSimStr);//将医生id和推荐的字符串值，写入到map中
			}
			return sortedAllDocSim;
		}
		
		/**
		 * 将获得相似的矩阵，根据相似度排序，取出recommendDocNum个医生，放入sortedAllDocSim中，并给予推荐
		 * @param doctorSimilarity 医生之间的相似度矩阵
		 * @param recommendDocNum,推荐的数量
		 * @return
		 */
			public HashMap<Integer,String> recommendSimDoc(double[][] doctorSimilarity,int recommendDocNum) {
				HashMap<Integer,String> sortedAllDocSim = new HashMap<Integer,String>();
				for (int i = 1; i < doctorSimilarity.length; i++) {
					double[] doctorSim = doctorSimilarity[i];//取出一个医生与所有医生的相似度
					// 相似度排序
					int[] sortedDocSim = Sort.similaritySortDouble(doctorSim);

					String docSimStr = "";
//					System.out.print("doc id:" + i + " ");
					int count = 0;
					int temp = 0;
					while (count < recommendDocNum && temp < sortedDocSim.length) {

						if (sortedDocSim[temp] == 0 || sortedDocSim[temp] == i || doctorSim[temp] <= 0) {//医生没有看病的情况
							temp++;
							continue;
						}
						//医生看过病，且将排序后的前recommendDocNum个，拼接成为一个字符串
						docSimStr += sortedDocSim[temp] + ",";
						// System.out.print(sortedDocSim[temp]+" ");
						temp++;
						count++;
					}
					if(docSimStr.length() >0){//去掉字符串最后的一个逗号
						docSimStr = docSimStr.substring(0, docSimStr.length() - 1);
					}
					
					// System.out.println();
//					System.out.println(docSimStr);
					sortedAllDocSim.put(i, docSimStr);//将医生id和推荐的字符串值，写入到map中
				}
				return sortedAllDocSim;
			}
}
