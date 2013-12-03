package net.wss.rs.dao.impl;

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

import net.wss.rs.DataSet;
import net.wss.rs.dao.BaseDao;
import net.wss.rs.entity.DiseaseEntity;
import net.wss.rs.entity.DoctorEntity;

public class BaseDaoImpl implements BaseDao{
	private static String PATH = "data.txt";
	private static String DOCTORPATH = "doctor.txt";
	private static String DISEASEPATH = "disease.txt";
//	@Override
	public DataSet loadDataSet() {
		DataSet ds = new DataSet();
//		ds.setAllDisease(readDiseaseTxtFile(DISEASEPATH));
//		ds.setAllDoctor(readDoctorTxtFile(DOCTORPATH));
		ds.setDiseaseByDoctorId(getDiseaseByDoctorId(PATH));
		return ds;

	}

	/*
	 * 读doctor数据集，得出有多少个医生
	 */
//	@Override
	public List<DoctorEntity> readDoctorTxtFile(String filePath) {

		List<DoctorEntity> allDoctor = new ArrayList<DoctorEntity>();
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

					String[] array = split(lineTxt);// 将读取得字符串分割，放入一个数组中

					DoctorEntity doctorEntity = new DoctorEntity();// 实例化一个医生实体
					doctorEntity.setId(Integer.valueOf(array[0]));// 设置医生的id为分割字符串后得到数组的第一列
					allDoctor.add(doctorEntity);// 将这个实体添加到一个专家集合
					System.out.println(array[0]);

				}
				read.close();

			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			} catch (FileNotFoundException e) {
			
				e.printStackTrace();
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}
		}
		return allDoctor;
	}

	/*
	 * 读disease数据集，获取有多少种病
	 */
//	@Override
	public List<DiseaseEntity> readDiseaseTxtFile(String filePath) {
		DataSet ds = new DataSet();
		List<DiseaseEntity> allDisease = new ArrayList<DiseaseEntity>();
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

					DiseaseEntity diseaseEntity = new DiseaseEntity();
					diseaseEntity.setId(Integer.valueOf(array[0]));
					allDisease.add(diseaseEntity);
					System.out.println(array[0]);

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
		return allDisease;
	}

	/*
	 * 读data(专家与疾病的关系)数据集
	 */
//	@Override
	public Map<Integer, List<Integer>> getDiseaseByDoctorId(
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

					Integer docId = Integer.parseInt(array[0]);
					Integer disId = Integer.parseInt(array[1]);
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
				
				e.printStackTrace();
			} catch (FileNotFoundException e) {
			
				e.printStackTrace();
			} catch (IOException e) {
				
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
	 * } catch (UnsupportedEncodingException e) { 
	 *	e.printStackTrace(); 
	 * }catch (FileNotFoundException e) { 
	 * e.printStackTrace(); 
	 * } catch (IOException e) {
	 * 
	 * 
	 * return diseaseByDoctorId; }
	 */

	/*
	 * 读data(专家与疾病的关系)数据集
	 */
//	@Override
	public Map<Integer, Integer> readTxtFile(String filePath) {

		Map<Integer, Integer> userMap = new HashMap<Integer, Integer>();
		// List<DiseaseEntity> allDisease = new ArrayList<DiseaseEntity>();
		// List<DoctorEntity> allDoctor = new ArrayList<DoctorEntity>();
		// Map<Integer, List<DiseaseEntity>> diseaseByDoctorId =new
		// HashMap<Integer, List<DiseaseEntity>>();

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
				
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}

		return userMap;
	}

	/*
	 * 将一串字符串用空格的形式分割，存储到一个数组当中
	 */
//	@Override
	public String[] split(String lineTxt) {
		String[] array = lineTxt.split("\\s+");
		// System.out.println(array[0]+" ,"+array[1]);
		return array;
	}
}
