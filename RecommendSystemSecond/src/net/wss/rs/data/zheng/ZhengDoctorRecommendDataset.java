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
	//测试数据的存放路径
	public static String REDOCDISPATH = "data/redocdiscount.txt";
	public static String DOCTORPATH = "data/doctor.txt";
	public static String DISEASEPATH = "data/disease.txt";

	String docPath;
	String zhengPath;
	String ratingPath;
	/**
	 * 所有症状的诊次
	 */
	public List<ZhengRatingEntity> allRating = new ArrayList<ZhengRatingEntity>();

	/**
	 * 得到所有的医生.
	 */
	public Map<Integer, DoctorEntity> allDoctor = new HashMap<Integer, DoctorEntity>();

	/**
	 * 得到所有的症状
	 */
	public Map<Integer, ZhengEntity> allZheng = new HashMap<Integer, ZhengEntity>();

	/**
	 * 通过症状的id得到所有症状的诊次
	 * 
	 */
	public Map<Integer, List<ZhengRatingEntity>> ratingsByZhengId = new HashMap<Integer, List<ZhengRatingEntity>>();

	/**
	 * 通过医生的id得到所有症状的诊次
	 */
	public Map<Integer, List<ZhengRatingEntity>> ratingsByDoctorId = new HashMap<Integer, List<ZhengRatingEntity>>();
	/**
	 * 通过医生id获得评分，根据评分从大到小排序
	 */
	public Map<Integer, List<ZhengRatingEntity>> sortedRatingsByDoctorId = new HashMap<Integer, List<ZhengRatingEntity>>();
	/**
	 * 测试数据源
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
	 * 数据库数据
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
	 * 读doctorall数据集，得出有多少个医生
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

					String[] array = split(lineTxt);// 将读取得字符串分割，放入一个数组中

					DoctorEntity doctorEntity = new DoctorEntity();// 实例化一个医生实体

					doctorEntity.setId(Integer.valueOf(array[0]));// 设置医生的id为分割字符串后得到数组的第一列
					if (array[1] != null) {
						doctorEntity.setName(array[1]);
					} else {
						doctorEntity.setName("");
					}
					allDoctor.put(doctorEntity.getId(), doctorEntity);// 将这个实体添加到一个专家集合
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
	 * 读zhengall数据集，获取有多少种病
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

					String[] array = split(lineTxt);// 将读取得字符串分割，放入一个数组中

					ZhengEntity zhengEntity = new ZhengEntity();// 实例化一个医生实体
					zhengEntity.setId(Integer.valueOf(array[0]));// 设置医生的id为分割字符串后得到数组的第一列
					zhengEntity.setName(array[1]);
					allZheng.put(zhengEntity.getId(), zhengEntity);// 将这个实体添加到一个专家集合
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
	 * 读取所有的ratingall
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

					String[] array = split(lineTxt);// 将读取得字符串分割，放入一个数组中
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
	 * 将诊次添加到map集合中
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
	 * 打印所有医生的key-value,key,value是医生的id
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
	 * 打印所有症状key-value，key,value是症状的id
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
	 * 打印所有诊次key-value，key,value是症状的id
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
	 * 打印出所有医生的相似个数
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
	 * 打印医生对症状的评分
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
	 * 将一串字符串用分号的形式分割，存储到一个数组当中
	 * 
	 * @param lineTxt
	 *            数据
	 * @return
	 */
	public static String[] split(String lineTxt) {
		// String[] array = lineTxt.split("\\s+");//空格的形式分割
		String[] array = lineTxt.split(";");//分号分割字符串
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
