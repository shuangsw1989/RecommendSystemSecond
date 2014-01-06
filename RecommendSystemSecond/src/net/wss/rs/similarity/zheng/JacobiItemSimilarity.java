package net.wss.rs.similarity.zheng;


import java.util.List;
import java.util.Map.Entry;
import net.wss.rs.data.zheng.ZhengDoctorRecommendDataset;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.ZhengRatingEntity;
import net.wss.rs.util.FileUtil;

public class JacobiItemSimilarity {
	/**
	 * ���������û������ƶ�
	 * ���㷽�����ſ˱Ⱦ���
	 * @param doctor1
	 * @param doctor2
	 * @return
	 */
	public double getSimilarityByJacobi(ZhengDoctorRecommendDataset ds ,DoctorEntity doctor1,
			DoctorEntity doctor2) {
		List<ZhengRatingEntity> dis1 = ds.getRatingsByDoctorId()
				.get(doctor1.getId());// ��ȡĳһ��ҽ���������ļ���
		List<ZhengRatingEntity> dis2 = ds.getRatingsByDoctorId()
				.get(doctor2.getId());
		
		int commDiseases=0;
		if (dis1 == null || dis2 == null) {
//			System.err.println("ҽ��û���ι���");
			return 0;
		}
		// �ж��������������Ƿ��н���
		double sim=0.0d;
		for (int i = 0; i < dis1.size(); i++) {
			for (int j = 0; j < dis2.size(); j++) {
				if (dis1.get(i).getZhengId() == dis2.get(j).getZhengId()) {
					commDiseases++;
				}
			}
		}
//		System.out.println("����ҽ��������ͬ�����ĸ�����"+commDiseases);
//		System.out.println("��һ��ҽ�����μ����ĸ�����"+dis1.size());	
//		System.out.println("�ڶ���ҽ�����μ����ĸ�����"+dis2.size());
		sim = commDiseases*1.0/(dis1.size()+dis2.size()-commDiseases);
//		System.out.println("�ſ˱����ƶȣ�"+String.format("%.5f",sim));	
		return sim;
	}
	
	/**
	 * ��������ҽ�������ƶ�
	 * �ſ˱�
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
					count = getSimilarityByJacobi(ds,doc1, doc2);// �ҳ����Ƶĸ���
					doctorSimilarity[i][j] = count;
					doctorSimilarity[j][i] = count;// ��Ϊ�Ǹ��ԳƵ�
				
			}
		}
//		д���ļ�
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
