package net.wss.rs.similarity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.wss.rs.DataSet;
import net.wss.rs.entity.DiseaseEntity;
import net.wss.rs.entity.DoctorEntity;
import net.wss.rs.entity.RatingEntity;

public class Data {
	private static String REDOCDISPATH = "data/redocdiscount.txt";
	private static String DOCTORPATH = "data/doctor.txt";
	private static String DISEASEPATH = "data/disease.txt";

	/**
	 * 下载数据集
	 * 
	 * @return
	 */
	public static DataSet loadDataSet() {
		
		DataSet ds = new DataSet();
		ds.setAllDisease(readDiseaseTxtFile(DISEASEPATH));
		ds.setAllDoctor(readDoctorTxtFile(DOCTORPATH));
		ds.setDiseaseByDoctorId(getDiseaseByDoctorId(REDOCDISPATH));
		return ds;

	}

	/*
	 * 读doctor数据集，得出有多少个医生
	 */
	public static Map<Integer,DoctorEntity> readDoctorTxtFile(String filePath) {

		Map<Integer,DoctorEntity> allDoctor = new HashMap<Integer,DoctorEntity>();
		File file = new File(filePath);
		
		if (file.isFile() && file.exists()) {
			String encoding = "GBK";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
//					System.out.println(lineTxt);
					if (lineTxt.length() == 0) {
						continue;
					}

					String[] array = split(lineTxt);// 将读取得字符串分割，放入一个数组中

					DoctorEntity doctorEntity = new DoctorEntity();// 实例化一个医生实体
					doctorEntity.setId(Integer.parseInt(array[0]));
					
					doctorEntity.setId(Integer.valueOf(array[0]));// 设置医生的id为分割字符串后得到数组的第一列
					allDoctor.put(doctorEntity.getId(), doctorEntity);// 将这个实体添加到一个专家集合
//					System.out.println(doctorEntity.getId());

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
		}else{
			System.err.println(file.getName()+" not exist");
		}
		return allDoctor;
	}

	/*
	 * 读disease数据集，获取有多少种病
	 */
	public static Map<Integer,DiseaseEntity> readDiseaseTxtFile(String filePath) {
		Map<Integer,DiseaseEntity> allDisease = new HashMap<Integer,DiseaseEntity>();
		File file = new File(filePath);
		
		if (file.isFile() && file.exists()) {
			String encoding = "GBK";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
//					System.out.println(lineTxt);
					if (lineTxt.length() == 0) {
						continue;
					}

					String[] array = split(lineTxt);// 将读取得字符串分割，放入一个数组中

					DiseaseEntity diseaseEntity = new DiseaseEntity();// 实例化一个医生实体
					diseaseEntity.setId(Integer.parseInt(array[0]));
					
					diseaseEntity.setId(Integer.valueOf(array[0]));// 设置医生的id为分割字符串后得到数组的第一列
					allDisease.put(diseaseEntity.getId(), diseaseEntity);// 将这个实体添加到一个专家集合
//					System.out.println(doctorEntity.getId());

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
		}else{
			System.err.println(file.getName()+" not exist");
		}
		return allDisease;
	}

	/*
	 * 读取所有的rating
	 */
	public static List<RatingEntity> loadRatings(String filePath) {
		List<RatingEntity> allRating = new ArrayList<RatingEntity>();
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			String encoding = "GBK";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
//					System.out.println(lineTxt);
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

			
		return allRating;
	}

	/*
	 * 读data(专家与疾病的关系)数据集
	 */
	public static Map<Integer, List<Integer>> getDiseaseByDoctorId(
			String filePath) {

		Map<Integer, List<Integer>> diseaseByDoctorId = new HashMap<Integer, List<Integer>>();

		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			String encoding = "GBK";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (lineTxt.length() == 0) {
						continue;
					}

					String[] array = split(lineTxt);

					Integer docId = Integer.parseInt(array[0]);//医生的编号
					Integer disId = Integer.parseInt(array[1]);//疾病的编号
					Integer treatmentTimes = Integer.parseInt(array[2]);//诊疗次数
					// DiseaseEntity dis = new DiseaseEntity();
					// dis.setId(Integer.parseInt(array[1]));

					if (diseaseByDoctorId.containsKey(docId)) {
						diseaseByDoctorId.get(docId).add(disId);
					} else {
						List<Integer> list = new ArrayList<Integer>();
						list.add(disId);
						diseaseByDoctorId.put(docId, list);
					}

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

		return diseaseByDoctorId;
	}

	
	
	/*
	 * public static Map<Integer, List<DiseaseEntity>>
	 * getDiseaseByDoctorId(String filePath) {
	 * 
	 * Map<Integer, List<DiseaseEntity>> diseaseByDoctorId =new HashMap<Integer,
	 * List<DiseaseEntity>>();
	 * 
	 * 
	 * File file = new File(filePath); if (file.isFile() && file.exists()) {
	 * String encoding = "GBK"; try { InputStreamReader read = new
	 * InputStreamReader( new FileInputStream(file), encoding); BufferedReader
	 * bufferedReader = new BufferedReader(read); String lineTxt = null; while
	 * ((lineTxt = bufferedReader.readLine()) != null) { if (lineTxt.length() ==
	 * 0) { continue; }
	 * 
	 * 
	 * String[] array = split(lineTxt); Integer docId =
	 * Integer.parseInt(array[0]); DiseaseEntity dis = new DiseaseEntity();
	 * dis.setId(Integer.parseInt(array[1]));
	 * 
	 * 
	 * if(diseaseByDoctorId.containsKey(docId)){
	 * diseaseByDoctorId.get(docId).add(dis); }else{ List<DiseaseEntity> list =
	 * new ArrayList<DiseaseEntity>(); list.add(dis);
	 * diseaseByDoctorId.put(docId, list); }
	 * 
	 * 
	 * } read.close();
	 * 
	 * } catch (UnsupportedEncodingException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } catch (FileNotFoundException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * return diseaseByDoctorId; }
	 */
	
	 private DoctorEntity getDoctor(Integer docId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 读data(专家与疾病的关系)数据集
	 */
	public static Map<Integer, Integer> readTxtFile(String filePath) {

		Map<Integer, Integer> userMap = new HashMap<Integer, Integer>();

		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			String encoding = "GBK";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (lineTxt.length() == 0) {
						continue;
					}
					String[] array = split(lineTxt);
					userMap.put(1, Integer.parseInt(array[0]));
					userMap.put(2, Integer.parseInt(array[1]));
					System.out.println(array[0]);

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

		return userMap;
	}

	/*
	 * 将一串字符串用空格的形式分割，存储到一个数组当中
	 */
	public static String[] split(String lineTxt) {
		String[] array = lineTxt.split("\\s+");
		// System.out.println(array[0]+" ,"+array[1]);
		return array;
	}
/*
 * 将诊次添加到map集合中
 */
	public void addRatingToMap(Map<Integer, List<RatingEntity>> map, Integer key,
			RatingEntity rating) {
		List<RatingEntity> ratingsForKey = map.get(key);
		if (ratingsForKey == null) {
			ratingsForKey = new ArrayList<RatingEntity>();
			map.put(key, ratingsForKey);
		}
		ratingsForKey.add(rating);
	}
	
	private void loadData(List<RatingEntity> ratings) {
		List<RatingEntity> allRating = new ArrayList<RatingEntity>();
		DataSet ds = new DataSet();
		
			/* Load all available ratings */
			if (ratings == null) {
				allRating = loadRatings(REDOCDISPATH);
			} else {
				allRating = ratings;
			}
			/* build maps that provide access to ratings by userId or itemId */
			for (RatingEntity rating : allRating) {
				addRatingToMap(ds.getRatingsByDiseaseId(), rating.getDiseaseId(), rating);
				addRatingToMap(ds.getRatingsByDoctorId(), rating.getDoctorId(), rating);
			}
	}
	/**
	 * 
	 * 传入两个用户，和疾病集合 返回两个医生治疗相同疾病的总数
	 * 
	 */
	public static int getSimilarity(DoctorEntity doctor1, DoctorEntity doctor2,
			Map<Integer, List<Integer>> diseaseByDoctorId) {
		List<Integer> dis1 = diseaseByDoctorId.get(doctor1.getId());// 获取某一个用户所包含的疾病
		List<Integer> dis2 = diseaseByDoctorId.get(doctor2.getId());
		// 如果这两个疾病集合有一个为空，则说明两个用户不相似，返回0
		if (dis1 == null || dis2 == null) {
			return 0;
		}

		int count = 0;
		// 判断两个疾病集合是否有交集，如果有则count+1
		for (int d1 : dis1) {
			for (int d2 : dis2) {
				if (d1 == d2) {
					// System.out.print(d1);
					count++;
					continue;// 继续当前循环
				}
			}
		}
		return count;
	}

	/**
	 * 得到所有医生治疗相同的疾病的个数（比较）
	 * 
	 * @param allDisease
	 * @param allDoctor
	 * @param diseaseByDoctorId
	 * @return
	 */
	public static int[][] getAllDoctorSimilartiy(
			List<DiseaseEntity> allDisease, List<DoctorEntity> allDoctor,
			Map<Integer, List<Integer>> diseaseByDoctorId) {

		int[][] doctorSimilarity = new int[allDoctor.size()][allDoctor.size()];
		for (int i = 0; i < allDoctor.size(); i++) {
			DoctorEntity doc1 = allDoctor.get(i);
			for (int j = 0; j < allDoctor.size(); j++) {
				DoctorEntity doc2 = allDoctor.get(j);
				if (i > j) {
					continue;// 跳出本次循环，执行下次循环
				}
				int count = getSimilarity(doc1, doc2, diseaseByDoctorId);// 找出相似的个数
				doctorSimilarity[i][j] = count;
				doctorSimilarity[j][i] = count;// 因为是个对称的
			}
		}

		return doctorSimilarity;
	}

	/**
	 * 打印所有医生的相似的个数。
	 * 对角线是医生治疗的疾病数；
	 * 其它代表医生与其它医生治疗相同疾病的数目
	 * 3 1 1 1 1 0 0 0 
	   1 3 1 0 2 0 0 0 
	   1 1 3 1 2 0 0 0 
	   1 0 1 2 0 0 0 0 
	   1 2 2 0 3 0 0 0 
	   0 0 0 0 0 0 0 0 
	   0 0 0 0 0 0 0 0 
	   0 0 0 0 0 0 0 0 
	 * @param allDoctor
	 * @param doctorSimilarity
	 */
	public static void printAllDoctorSimilartiy(List<DoctorEntity> allDoctor,
			int[][] doctorSimilarity) {
		for (int i = 0; i < allDoctor.size(); i++) {

			for (int j = 0; j < allDoctor.size(); j++) {
				System.out.print(doctorSimilarity[i][j] + " ");
			}
			System.out.println();
		}
	}

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
	/**
	 * 打印排序后的结果，及其排序后下标的结果
	 * @param value
	 * @param index
	 */
	public static void printSortedSimilarity(int[] value,int[] index){
		for (int i = 0; i < index.length; i++) {
			System.out.print(index[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < value.length; i++) {
			System.out.print(value[i] + " ");
		}
		System.out.println();
	}
	/**
	 * 打印排序后的下标
	 * @param index
	 */
	public static void printSortedSimilarity(int[] index){
		for (int i = 0; i < index.length; i++) {
			System.out.print(index[i] + " ");
		}
		System.out.println();
		
	}
	/**
	 * 对所有医生的相似性进行排序
	 * @param doctorSimilarity
	 * @return
	 */
	public static int[][] similaritySort(int[][] doctorSimilarity){
			
		int[][] sortedindex = new int[doctorSimilarity.length][doctorSimilarity[0].length];
		
		for(int i = 0;i<sortedindex.length;i++){

				int[] index = similaritySort(doctorSimilarity[i]);
				sortedindex[i] =index;
		}
		return sortedindex;
	}
	
	
	public static void main(String[] args) {
		DataSet ds = loadDataSet();
//		System.out.println("dajiahao");
		/*
		 * 读取所有用户的测试
		 */
//		Map<Integer,DoctorEntity> allDoctor = ds.getAllDoctor();
//		System.out.println("Doctor数量："+allDoctor.size());
//		System.out.println("Doctor的id:");
//		ds.printAllDoctor();
		
		/*
		 * 读取所有疾病的测试
		 */
//		Map<Integer,DiseaseEntity> allDisease = ds.getAllDisease();
//		System.out.println("Disease数量："+allDisease.size());
//		System.out.println("Disease的id:");
//		ds.printAllDisease();
      /*
       * 根据某个用户治疗的疾病的诊次
       */


//		int[][] doctorSimilarity = getAllDoctorSimilartiy(ds.getAllDisease(),
//				ds.getAllDoctor(), ds.getDiseaseByDoctorId());
//		printAllDoctorSimilartiy(ds.getAllDoctor(), doctorSimilarity);
//		
//		int[][] allIndex = similaritySort(doctorSimilarity);
//		
//		for (int i = 0; i < allIndex.length - 1; i++) {
//			for (int j = 0; j < allIndex[i].length; j++) {
//				System.out.print(allIndex[i][j] + " ");
//			}
//			System.out.println();
//		}
		
//		int[] index = similaritySort(doctorSimilarity[1]);
//		printSortedSimilarity(index);
		
	}
}
