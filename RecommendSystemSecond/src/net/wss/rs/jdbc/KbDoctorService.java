package net.wss.rs.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class KbDoctorService {
	DBConnection db;
	ResultSet rs = null;
	Statement stmt = null;
	Connection conn=null;
	
	public KbDoctorService(DBConnection db) {
		this.db = db;
	}

	public KbDoctorService() {
		this.db = new DBConnection();
	}

/**
 * 连接数据库，更新kb_doctor表中相似的一列
 * @param docid  医生id
 * @param simStr 推荐医生的id字符串
 */
	public void updateDocSim(int docid,String simStr) {
		
		try {
			// 数据库连接的获取
			Connection conn = db.getConn();
			stmt = conn.createStatement();
			// 数据库sql语句的拼接
			String sql = "UPDATE kb_doctor set sim_doctor_disease='"+simStr+"' WHERE id ="+docid;
			int rs = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		db.close(rs);
		db.close(stmt);
		db.close(conn);

	}
}
