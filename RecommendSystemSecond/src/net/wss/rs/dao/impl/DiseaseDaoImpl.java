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

import net.wss.rs.DataSet;
import net.wss.rs.dao.BaseDao;
import net.wss.rs.dao.DiseaseDao;
import net.wss.rs.entity.DiseaseEntity;


public class DiseaseDaoImpl implements DiseaseDao {
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
					BaseDao bd = new BaseDaoImpl();
					String[] array = bd.split(lineTxt);

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

}
