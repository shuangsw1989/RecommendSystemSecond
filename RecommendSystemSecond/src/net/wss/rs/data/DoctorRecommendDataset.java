package net.wss.rs.data;

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
import java.util.regex.Pattern;

import net.wss.rs.entity.DiseaseEntity;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.RatingEntity;
import net.wss.rs.jdbc.ReadDBTable;
import net.wss.rs.util.StringUtil;

public class DoctorRecommendDataset {

	public static String REDOCDISPATH = "data/redocdiscount.txt";
	public static String DOCTORPATH = "data/doctor.txt";
	public static String DISEASEPATH = "data/disease.txt";

	String docPath;
	String disPath;
	String ratingPath;
	/**
	 * 所有疾病的诊次
	 */
	public List<RatingEntity> allRating = new ArrayList<RatingEntity>();

	/**
	 * 得到所有的医生.
	 */
	public Map<Integer, DoctorEntity> allDoctor = new HashMap<Integer, DoctorEntity>();

	/**
	 * 得到所有的疾病
	 */
	public Map<Integer, DiseaseEntity> allDisease = new HashMap<Integer, DiseaseEntity>();

	/**
	 * 通过疾病的id得到所有疾病的诊次
	 * 
	 */
	public Map<Integer, List<RatingEntity>> ratingsByDiseaseId = new HashMap<Integer, List<RatingEntity>>();

	/**
	 * 通过医生的id得到所有疾病的诊次
	 */
	public Map<Integer, List<RatingEntity>> ratingsByDoctorId = new HashMap<Integer, List<RatingEntity>>();

	/**
	 * 测试数据源
	 */
	public DoctorRecommendDataset() {
		this.docPath=DOCTORPATH;
		this.disPath = DISEASEPATH;
		this.ratingPath = REDOCDISPATH;
		
		readDoctorTxtFile(DOCTORPATH);
		readDiseaseTxtFile(DISEASEPATH);
		loadRatings(REDOCDISPATH);

	}
	
	/**
	 * 数据库数据
	 * @param docPath
	 * @param disPath
	 * @param ratingPath
	 */
	public DoctorRecommendDataset(String docPath,String disPath,String ratingPath) {
		this.docPath=docPath;
		this.disPath = disPath;
		this.ratingPath = ratingPath;
		readDoctorTxtFile(docPath);
		readDiseaseTxtFile(disPath);
		loadRatings(ratingPath);

	}


	/**
	 * 读doctor数据集，得出有多少个医生
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println(file.getName() + " not exist");
		}
	}

	/**
	 * 读disease数据集，获取有多少种病
	 */
	public void readDiseaseTxtFile(String filePath) {

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

					DiseaseEntity diseaseEntity = new DiseaseEntity();// 实例化一个医生实体
					diseaseEntity.setId(Integer.parseInt(array[0]));

					diseaseEntity.setId(Integer.valueOf(array[0]));// 设置医生的id为分割字符串后得到数组的第一列
					allDisease.put(diseaseEntity.getId(), diseaseEntity);// 将这个实体添加到一个专家集合
					// System.out.println(doctorEntity.getId());

				}
				read.close();

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println(file.getName() + " not exist");
		}

	}

	
	

	/**
	 * 读取所有的rating
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
					int docId = Integer.parseInt(array[0]);
					int disId = Integer.parseInt(array[1]);
					int rating = Integer.parseInt(array[2]);
					allRating.add(new RatingEntity(docId, disId, rating));
				}
				read.close();

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/* build maps that provide access to ratings by userId or itemId */
		for (RatingEntity rating : allRating) {
			addRatingToMap(ratingsByDiseaseId, rating.getDiseaseId(), rating);
			addRatingToMap(ratingsByDoctorId, rating.getDoctorId(), rating);
		}

	}

	/**
	 * 将诊次添加到map集合中
	 */
	public void addRatingToMap(Map<Integer, List<RatingEntity>> map,
			Integer key, RatingEntity rating) {
		List<RatingEntity> ratingsForKey = map.get(key);
		if (ratingsForKey == null) {
			ratingsForKey = new ArrayList<RatingEntity>();
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
	 * 打印所有疾病key-value，key,value是疾病的id
	 */
	public void printAllDisease() {

		for (Entry<Integer, DiseaseEntity> entry : allDisease.entrySet()) {
			Integer disKey = entry.getKey();
			DiseaseEntity value = entry.getValue();

			System.out.println("dis id:" + value.getId());
		}
	}

	/**
	 * 打印所有诊次key-value，key,value是疾病的id
	 */
	public void printAllRating() {

		for (Entry<Integer, DoctorEntity> entry : allDoctor.entrySet()) {
			Integer docKey = entry.getKey();
			System.out.print("doc id:" + docKey + "  ");
			List<RatingEntity> docRatingList = ratingsByDoctorId.get(docKey);
			if (docRatingList == null) {
				System.out.println();
				continue;
			}
			for (RatingEntity rating : docRatingList) {
				System.out.print(rating.getDiseaseId() + ","
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
	 * 打印医生对疾病的评分
	 */
	public void printAllRatingMatrix() {
		int[][] ratingMatrix = new int[allDoctor.size()][allDisease.size()];
		for (Entry<Integer, DoctorEntity> doctorEntry : allDoctor.entrySet()) {
			for (Entry<Integer, DiseaseEntity> entry1 : allDisease.entrySet()) {
				Integer docKey = doctorEntry.getKey();
				// System.out.print( "doc id:"+key+"  ");
				// List<RatingEntity> docRatingList =
				// ratingsByDoctorId.get(key);
				Integer disKey = entry1.getKey();
				// System.out.print( "dis id:"+key1+"  ");
				List<RatingEntity> disRatingList = ratingsByDoctorId
						.get(docKey);
				if (disRatingList == null) {
					System.out.print(0 + " ");
					continue;
				}
				int r = 0;
				for (RatingEntity rating : disRatingList) {
					if (rating.getDiseaseId() == disKey) {
						r = rating.getRating();
					}
				}
				System.out.print(r + " ");
			}
			System.out.println();
		}
	}

	// int[][] ratingMatrix = new int[allDoctor.size()][allDisease.size()];
	// System.out.println("suoyouyonghu "+allDoctor.size());
	// System.out.println("suoyoujibing "+allDisease.size());
	// for (int i = 0; i < allDoctor.size(); i++) {
	// System.out.println("ratingsByDoctorId"+ratingsByDoctorId.get(i));
	// for (int j = 0; j < allDisease.size(); j++) {
	// System.out.println("医生有关系否"+ratingsByDoctorId.get(i).get(j));
	// if(ratingsByDoctorId.get(i).get(j)==null){
	//
	// ratingMatrix[i][j]=0;
	// System.out.println(ratingMatrix[i][j]+" ");
	//
	// }else{
	// ratingMatrix[i][j]=ratingsByDoctorId.get(i).get(j).getRating();
	// System.out.print(ratingMatrix[i][j] + " ");
	// }
	// }
	// System.out.println();
	// }

	/**
	 * 将一串字符串用分号的形式分割，存储到一个数组当中
	 * 
	 * @param lineTxt
	 *            数据
	 * @return
	 */
	public static String[] split(String lineTxt) {
		// String[] array = lineTxt.split("\\s+");//空格的形式分割
		String[] array = lineTxt.split(";");
		// System.out.println(array[0]+" ,"+array[1]);
		return array;
	}

	public List<RatingEntity> getAllRating() {
		return allRating;
	}

	public void setAllRating(List<RatingEntity> allRating) {
		this.allRating = allRating;
	}

	public Map<Integer, DoctorEntity> getAllDoctor() {
		return allDoctor;
	}

	public void setAllDoctor(Map<Integer, DoctorEntity> allDoctor) {
		this.allDoctor = allDoctor;
	}

	public Map<Integer, DiseaseEntity> getAllDisease() {
		return allDisease;
	}

	public void setAllDisease(Map<Integer, DiseaseEntity> allDisease) {
		this.allDisease = allDisease;
	}

	public Map<Integer, List<RatingEntity>> getRatingsByDiseaseId() {
		return ratingsByDiseaseId;
	}

	public void setRatingsByDiseaseId(
			Map<Integer, List<RatingEntity>> ratingsByDiseaseId) {
		this.ratingsByDiseaseId = ratingsByDiseaseId;
	}

	public Map<Integer, List<RatingEntity>> getRatingsByDoctorId() {
		return ratingsByDoctorId;
	}

	public void setRatingsByDoctorId(
			Map<Integer, List<RatingEntity>> ratingsByDoctorId) {
		this.ratingsByDoctorId = ratingsByDoctorId;
	}

}
