package net.wss.rs.data.zheng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.wss.rs.entity.ZhengEntity;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.ZhengRatingEntity;


public class ZhengDoctorRecommendDataset {
	//�������ݵĴ��·��
	public static String REDOCDISPATH = "data/redocdiscount.txt";
	public static String DOCTORPATH = "data/doctor.txt";
	public static String DISEASEPATH = "data/disease.txt";

	String docPath;
	String zhengPath;
	String ratingPath;
	/**
	 * ����֢״�����
	 */
	public List<ZhengRatingEntity> allRating = new ArrayList<ZhengRatingEntity>();

	/**
	 * �õ����е�ҽ��.
	 */
	public Map<Integer, DoctorEntity> allDoctor = new HashMap<Integer, DoctorEntity>();

	/**
	 * �õ����е�֢״
	 */
	public Map<Integer, ZhengEntity> allZheng = new HashMap<Integer, ZhengEntity>();

	/**
	 * ͨ��֢״��id�õ�����֢״�����
	 * 
	 */
	public Map<Integer, List<ZhengRatingEntity>> ratingsByZhengId = new HashMap<Integer, List<ZhengRatingEntity>>();

	/**
	 * ͨ��ҽ����id�õ�����֢״�����
	 */
	public Map<Integer, List<ZhengRatingEntity>> ratingsByDoctorId = new HashMap<Integer, List<ZhengRatingEntity>>();
	/**
	 * ͨ��ҽ��id������֣��������ִӴ�С����
	 */
	public Map<Integer, List<ZhengRatingEntity>> sortedRatingsByDoctorId = new HashMap<Integer, List<ZhengRatingEntity>>();
	/**
	 * ��������Դ
	 */
//	public ZhengDoctorRecommendDataset() {
//		this.docPath=DOCTORPATH;
//		this.zhengPath = ZHENGPATH;
//		this.ratingPath = REDOCDISPATH;
//		
//		readDoctorTxtFile(DOCTORPATH);
//		readDiseaseTxtFile(ZHENGPATH);
//		loadRatings(REDOCDISPATH);
//
//	}
	
	/**
	 * ���ݿ�����
	 * @param docPath
	 * @param zhengPath
	 * @param ratingPath
	 */
	public ZhengDoctorRecommendDataset(String docPath,String zhengPath,String ratingPath) {
		this.docPath=docPath;
		this.zhengPath=zhengPath;
		this.ratingPath = ratingPath;
		readDoctorTxtFile(docPath);
		readZhengTxtFile(zhengPath);
		loadRatings(ratingPath);

	}


	/**
	 * ��doctorall���ݼ����ó��ж��ٸ�ҽ��
	 */
	public void readDoctorTxtFile(String filePath) {

		// Map<Integer,DoctorEntity> allDoctor = new
		// HashMap<Integer,DoctorEntity>();
		File file = new File(filePath);

		if (file.isFile() && file.exists()) {
			String encoding = "GBK";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					if (lineTxt.length() == 0) {
						continue;
					}

					String[] array = split(lineTxt);// ����ȡ���ַ����ָ����һ��������

					DoctorEntity doctorEntity = new DoctorEntity();// ʵ����һ��ҽ��ʵ��

					doctorEntity.setId(Integer.valueOf(array[0]));// ����ҽ����idΪ�ָ��ַ�����õ�����ĵ�һ��
					if (array[1] != null) {
						doctorEntity.setName(array[1]);
					} else {
						doctorEntity.setName("");
					}
					allDoctor.put(doctorEntity.getId(), doctorEntity);// �����ʵ����ӵ�һ��ר�Ҽ���
					// System.out.println(doctorEntity.getId());

				}
				read.close();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println(file.getName() + " not exist");
		}
	}

	/**
	 * ��zhengall���ݼ�����ȡ�ж����ֲ�
	 */
	public void readZhengTxtFile(String filePath) {

		File file = new File(filePath);

		if (file.isFile() && file.exists()) {
			String encoding = "GBK";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					if (lineTxt.length() == 0) {
						continue;
					}

					String[] array = split(lineTxt);// ����ȡ���ַ����ָ����һ��������

					ZhengEntity zhengEntity = new ZhengEntity();// ʵ����һ��ҽ��ʵ��
					zhengEntity.setId(Integer.valueOf(array[0]));// ����ҽ����idΪ�ָ��ַ�����õ�����ĵ�һ��
					zhengEntity.setName(array[1]);
					allZheng.put(zhengEntity.getId(), zhengEntity);// �����ʵ����ӵ�һ��ר�Ҽ���
					// System.out.println(doctorEntity.getId());

				}
				read.close();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println(file.getName() + " not exist");
		}

	}

	
	

	/**
	 * ��ȡ���е�ratingall
	 */
	public void loadRatings(String filePath) {

		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			String encoding = "GBK";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					if (lineTxt.length() == 0) {
						continue;
					}

					String[] array = split(lineTxt);// ����ȡ���ַ����ָ����һ��������
					if(array.length==3&&array[0].length()>0&&array[1].length()>0&&array[2].length()>0){
						int docId = Integer.parseInt(array[0]);
						int zhengId = Integer.parseInt(array[1]);
						int rating = Integer.parseInt(array[2]);
						allRating.add(new ZhengRatingEntity(docId, zhengId, rating));
					}
				}
				read.close();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/* build maps that provide access to ratings by userId or itemId */
		for (ZhengRatingEntity rating : allRating) {
			addRatingToMap(ratingsByZhengId, rating.getZhengId(), rating);
			addRatingToMap(ratingsByDoctorId, rating.getDoctorId(), rating);
		}

	}

	/**
	 * �������ӵ�map������
	 */
	public void addRatingToMap(Map<Integer, List<ZhengRatingEntity>> map,
			Integer key, ZhengRatingEntity rating) {
		List<ZhengRatingEntity> ratingsForKey = map.get(key);
		if (ratingsForKey == null) {
			ratingsForKey = new ArrayList<ZhengRatingEntity>();
			map.put(key, ratingsForKey);
		}
		ratingsForKey.add(rating);
	}

	/**
	 * ��ӡ����ҽ����key-value,key,value��ҽ����id
	 */
	public void printAllDoctor() {

		for (Entry<Integer, DoctorEntity> entry : allDoctor.entrySet()) {
			Integer key = entry.getKey();
			DoctorEntity value = entry.getValue();

			System.out.println("doc id:" + value.getId() + "  doc name:"
					+ value.getName());
		}
	}

	/**
	 * ��ӡ����֢״key-value��key,value��֢״��id
	 */
	public void printAllZheng() {

		for (Entry<Integer, ZhengEntity> entry : allZheng.entrySet()) {
			Integer zhengKey = entry.getKey();
			ZhengEntity value = entry.getValue();

			System.out.println("zheng id:" + value.getId()+ "  zheng name:"
					+ value.getName());
		}
	}

	/**
	 * ��ӡ�������key-value��key,value��֢״��id
	 */
	public void printAllRating() {

		for (Entry<Integer, DoctorEntity> entry : allDoctor.entrySet()) {
			Integer docKey = entry.getKey();
			System.out.print("doc id:" + docKey + "  ");
			List<ZhengRatingEntity> docRatingList = ratingsByDoctorId.get(docKey);
			if (docRatingList == null) {
				System.out.println();
				continue;
			}
			for (ZhengRatingEntity rating : docRatingList) {
				System.out.print(rating.getZhengId() + ","
						+ rating.getRating() + "  ");
			}
			System.out.println();
		}
	}

	/**
	 * ��ӡ������ҽ�������Ƹ���
	 * 
	 * @param allDoctor
	 * @param doctorSimilarity
	 */
	public void printAllDoctorSimilartiy(List<DoctorEntity> allDoctor,
			int[][] doctorSimilarity) {
		for (int i = 0; i < allDoctor.size(); i++) {

			for (int j = 0; j < allDoctor.size(); j++) {
				System.out.print(doctorSimilarity[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * ��ӡҽ����֢״������
	 */
	public void printAllRatingMatrix() {
		int[][] ratingMatrix = new int[allDoctor.size()][allZheng.size()];
		for (Entry<Integer, DoctorEntity> doctorEntry : allDoctor.entrySet()) {
			for (Entry<Integer, ZhengEntity> entry1 : allZheng.entrySet()) {
				Integer docKey = doctorEntry.getKey();
				// System.out.print( "doc id:"+key+"  ");
				// List<RatingEntity> docRatingList =
				// ratingsByDoctorId.get(key);
				Integer zhengKey = entry1.getKey();
				// System.out.print( "zheng id:"+key1+"  ");
				List<ZhengRatingEntity> zhengRatingList = ratingsByDoctorId
						.get(docKey);
				if (zhengRatingList == null) {
					System.out.print(0 + " ");
					continue;
				}
				int r = 0;
				for (ZhengRatingEntity rating : zhengRatingList) {
					if (rating.getZhengId() == zhengKey) {
						r = rating.getRating();
					}
				}
				System.out.print(r + " ");
			}
			System.out.println();
		}
	}

	/**
	 * ��һ���ַ����÷ֺŵ���ʽ�ָ�洢��һ�����鵱��
	 * 
	 * @param lineTxt
	 *            ����
	 * @return
	 */
	public static String[] split(String lineTxt) {
		// String[] array = lineTxt.split("\\s+");//�ո����ʽ�ָ�
		String[] array = lineTxt.split(";");//�ֺŷָ��ַ���
		// System.out.println(array[0]+" ,"+array[1]);
		return array;
	}

	public List<ZhengRatingEntity> getAllRating() {
		return allRating;
	}

	public void setAllRating(List<ZhengRatingEntity> allRating) {
		this.allRating = allRating;
	}

	public Map<Integer, DoctorEntity> getAllDoctor() {
		return allDoctor;
	}

	public void setAllDoctor(Map<Integer, DoctorEntity> allDoctor) {
		this.allDoctor = allDoctor;
	}

	public Map<Integer, ZhengEntity> getAllDisease() {
		return allZheng;
	}

	public void setAllDisease(Map<Integer, ZhengEntity> allZheng) {
		this.allZheng = allZheng;
	}

	public Map<Integer, List<ZhengRatingEntity>> getRatingsByDiseaseId() {
		return ratingsByZhengId;
	}

	public void setRatingsByDiseaseId(
			Map<Integer, List<ZhengRatingEntity>> ratingsByZhengId) {
		this.ratingsByZhengId = ratingsByZhengId;
	}

	public Map<Integer, List<ZhengRatingEntity>> getRatingsByDoctorId() {
		return ratingsByDoctorId;
	}

	public void setRatingsByDoctorId(
			Map<Integer, List<ZhengRatingEntity>> ratingsByDoctorId) {
		this.ratingsByDoctorId = ratingsByDoctorId;
	}

	public Map<Integer, List<ZhengRatingEntity>> getSortedRatingsByDoctorId() {
		return sortedRatingsByDoctorId;
	}

	public void setSortedRatingsByDoctorId(
			Map<Integer, List<ZhengRatingEntity>> sortedRatingsByDoctorId) {
		this.sortedRatingsByDoctorId = sortedRatingsByDoctorId;
	}

}
