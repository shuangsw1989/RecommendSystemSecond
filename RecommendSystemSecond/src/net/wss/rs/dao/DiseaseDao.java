package net.wss.rs.dao;

import java.util.List;

import net.wss.rs.entity.DiseaseEntity;


public interface DiseaseDao {

	List<DiseaseEntity> readDiseaseTxtFile(String filePath);

	

}
