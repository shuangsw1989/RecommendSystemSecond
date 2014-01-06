package net.wss.rs.similarity.zheng;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;
import net.wss.rs.data.zheng.ZhengDoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.ZhengRatingEntity;
import net.wss.rs.util.FileUtil;

public class EucliddistanceItemSimilarity {

	/**
	 * ���������û������ƶ�
	 * ����ķ�����ŷʽ����ķ���
	 * @param doctor1
	 * @param doctor2
	 * @return
	 */
	
	public double getSimilarityByEucliddistance(ZhengDoctorRecommendDataset ds,DoctorEntity doctor1,
			DoctorEntity doctor2) {
		List<ZhengRatingEntity> zheng1 = ds.getRatingsByDoctorId()
				.get(doctor1.getId());// ��ȡĳһ��ҽ���������ļ���
		List<ZhengRatingEntity> zheng2 = ds.getRatingsByDoctorId()
				.get(doctor2.getId());
		
		int commZheng=0;
		if (zheng1 == null || zheng2 == null) {
//			System.err.println("ҽ��û���ι���");
			return 0;
		}
		
		
//		for (int i = 0; i < zheng1.size(); i++) {
//			System.out.print(zheng1.get(i).getZhengId()+":"+zheng1.get(i).getRating()+" ");			
//		}
//		System.out.println();
//		for (int i = 0; i < zheng2.size(); i++) {
//			System.out.print(zheng2.get(i).getZhengId()+":"+zheng2.get(i).getRating()+" ");			
//		}
//		System.out.println();
//		

		// �ж��������������Ƿ��н���
		double sim=0.0;
		
		for (int i = 0; i < zheng1.size(); i++) {
			for (int j = 0; j < zheng2.size(); j++) {
				if (zheng1.get(i).getZhengId() == zheng2.get(j).getZhengId()) {
					commZheng++;
					int zheng1Rating = zheng1.get(i).getRating();
					int zheng2Rating = zheng2.get(j).getRating();
//					System.out.println("common zheng id:"+zheng1.get(i).getZhengId()+" "+zheng1Rating+" "+zheng2Rating);
					sim += Math.pow((zheng1Rating-zheng2Rating),2);
				}
			}
		}
//		System.out.println("ŷʽ�������ƶȣ�"+sim);
		if(commZheng>0){
//			sim=1.0d - Math.tanh(sim);
//			sim=1.0/(sim+1);
			sim=sim;
		}
		
		return sim;
	}
	/**
	 * ��������ҽ�������ƶ�
	 * 
	 * ŷʽ����
	 *
	 */
	
	public double[][] getAllDoctorSimilarity(ZhengDoctorRecommendDataset ds) {
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
					continue;// ��������ѭ����ִ���´�ѭ��
				}
//				System.out.println(doc1.getId());
//				System.out.println(doc2.getId());
				double count=0;
			
					count = getSimilarityByEucliddistance(ds,doc1, doc2);// �ҳ����Ƶĸ���
					doctorSimilarity[i][j] = count;
					doctorSimilarity[j][i] = count;// ��Ϊ�Ǹ��ԳƵ�
				
			}
		}
		
//		File file = new File(DataSetConfig.SimiResultPath);
//		if (file.exists()) {		
//			file.delete();
//		} 
//		
//		for (int i = 0; i < doctorSimilarity.length; i++) {
//			String line="";
//			for (int j = 0; j < doctorSimilarity[i].length; j++) {
//				line +=String.format("%.4f",doctorSimilarity[i][j]) + " ";
////				System.out.print(String.format("%.4f",doctorSimilarity[i][j]) + " ");
//			}
//			System.out.println();
//			FileUtil.appendData(DataSetConfig.SimiResultPath, line);
//		}
	
		return doctorSimilarity;
	}
	
}
