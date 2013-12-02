package net.wss.rs.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import net.wss.rs.dao.BaseDao;
import net.wss.rs.dao.DoctorDao;

import net.wss.rs.entity.DoctorEntity;

public class DoctorDaoImpl implements DoctorDao {

	/*
	 * 读doctor数据集，得出有多少个医生
	 */
	// @Override
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
					BaseDao bd = new BaseDaoImpl();
					String[] array = bd.split(lineTxt);// 将读取得字符串分割，放入一个数组中

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

}
