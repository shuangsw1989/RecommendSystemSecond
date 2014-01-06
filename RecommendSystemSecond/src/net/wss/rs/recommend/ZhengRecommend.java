package net.wss.rs.recommend;

import java.util.HashMap;

import net.wss.rs.util.Sort;

public class ZhengRecommend {
	/**
	 * ��������Ƶľ��󣬸������ƶ�����ȡ��recommendDocNum��ҽ��������sortedAllDocSim�У��������Ƽ�
	 * @param doctorSimilarity ҽ��֮������ƶȾ���
	 * @param recommendDocNum,�Ƽ�������
	 * @return
	 */
		public HashMap<Integer,String> recommendSimDoc(int[][] doctorSimilarity,int recommendDocNum) {
			HashMap<Integer,String> sortedAllDocSim = new HashMap<Integer,String>();
			for (int i = 1; i < doctorSimilarity.length; i++) {
				int[] doctorSim = doctorSimilarity[i];//ȡ��һ��ҽ��������ҽ�������ƶ�
				// ���ƶ�����
				int[] sortedDocSim = Sort.similaritySort(doctorSim);

				String docSimStr = "";
//				System.out.print("doc id:" + i + " ");
				int count = 0;
				int temp = 0;
				while (count < recommendDocNum && temp < sortedDocSim.length) {

					if (sortedDocSim[temp] == 0 || sortedDocSim[temp] == i || doctorSim[temp] <= 0) {//ҽ��û�п��������
						temp++;
						continue;
					}
					//ҽ�����������ҽ�������ǰrecommendDocNum����ƴ�ӳ�Ϊһ���ַ���
					docSimStr += sortedDocSim[temp] + ",";
					// System.out.print(sortedDocSim[temp]+" ");
					temp++;
					count++;
				}
				if(docSimStr.length() >0){//ȥ���ַ�������һ������
					docSimStr = docSimStr.substring(0, docSimStr.length() - 1);
				}
				
				// System.out.println();
//				System.out.println(docSimStr);
				sortedAllDocSim.put(i, docSimStr);//��ҽ��id���Ƽ����ַ���ֵ��д�뵽map��
			}
			return sortedAllDocSim;
		}
		
		/**
		 * ��������Ƶľ��󣬸������ƶ�����ȡ��recommendDocNum��ҽ��������sortedAllDocSim�У��������Ƽ�
		 * @param doctorSimilarity ҽ��֮������ƶȾ���
		 * @param recommendDocNum,�Ƽ�������
		 * @return
		 */
			public HashMap<Integer,String> recommendSimDoc(double[][] doctorSimilarity,int recommendDocNum) {
				HashMap<Integer,String> sortedAllDocSim = new HashMap<Integer,String>();
				for (int i = 1; i < doctorSimilarity.length; i++) {
					double[] doctorSim = doctorSimilarity[i];//ȡ��һ��ҽ��������ҽ�������ƶ�
					// ���ƶ�����
					int[] sortedDocSim = Sort.similaritySortDouble(doctorSim);

					String docSimStr = "";
//					System.out.print("doc id:" + i + " ");
					int count = 0;
					int temp = 0;
					while (count < recommendDocNum && temp < sortedDocSim.length) {

						if (sortedDocSim[temp] == 0 || sortedDocSim[temp] == i || doctorSim[temp] <= 0) {//ҽ��û�п��������
							temp++;
							continue;
						}
						//ҽ�����������ҽ�������ǰrecommendDocNum����ƴ�ӳ�Ϊһ���ַ���
						docSimStr += sortedDocSim[temp] + ",";
						// System.out.print(sortedDocSim[temp]+" ");
						temp++;
						count++;
					}
					if(docSimStr.length() >0){//ȥ���ַ�������һ������
						docSimStr = docSimStr.substring(0, docSimStr.length() - 1);
					}
					
					// System.out.println();
//					System.out.println(docSimStr);
					sortedAllDocSim.put(i, docSimStr);//��ҽ��id���Ƽ����ַ���ֵ��д�뵽map��
				}
				return sortedAllDocSim;
			}
}
