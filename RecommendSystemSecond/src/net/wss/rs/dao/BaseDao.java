package net.wss.rs.dao;

import java.util.List;
import java.util.Map;

import net.wss.rs.DataSet;
import net.wss.rs.entity.DiseaseEntity;
import net.wss.rs.entity.DoctorEntity;

public interface BaseDao {

//	List<DoctorEntity> readDoctorTxtFile(String filePath);

	String[] split(String lineTxt);

	Map<Integer, Integer> readTxtFile(String filePath);

	Map<Integer, List<Integer>> getDiseaseByDoctorId(String filePath);

//	List<DiseaseEntity> readDiseaseTxtFile(String filePath);

	DataSet loadDataSet();

}
