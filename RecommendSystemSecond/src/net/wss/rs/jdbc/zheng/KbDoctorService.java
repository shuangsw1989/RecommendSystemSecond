package net.wss.rs.jdbc.zheng;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.wss.rs.jdbc.DBConnection;

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
 * �������ݿ⣬����kb_doctor�������Ƶ�һ��
 * @param docid  ҽ��id
 * @param simStr �Ƽ�ҽ����id�ַ���
 */
	public void updateDocSim(int docid,String simStr) {
		
		try {
			// ���ݿ����ӵĻ�ȡ
			Connection conn = db.getConn();
			stmt = conn.createStatement();
			// ���ݿ�sql����ƴ��
			String sql = "UPDATE kb_doctor set sim_doctor_zheng='"+simStr+"' WHERE id ="+docid;
			int rs = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		db.close(rs);
		db.close(stmt);
		db.close(conn);

	}
}
