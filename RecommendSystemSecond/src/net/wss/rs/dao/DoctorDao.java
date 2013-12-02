package net.wss.rs.dao;

import java.util.List;

import net.wss.rs.entity.DoctorEntity;

public interface DoctorDao {
	List<DoctorEntity> readDoctorTxtFile(String filePath);

}
