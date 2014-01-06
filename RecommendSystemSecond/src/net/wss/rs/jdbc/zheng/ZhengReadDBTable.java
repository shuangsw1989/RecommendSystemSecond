package net.wss.rs.jdbc.zheng;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.wss.rs.data.zheng.ZhengDataSetConfig;
import net.wss.rs.jdbc.DBConnection;
import net.wss.rs.util.FileUtil;

public class ZhengReadDBTable {
	
	DBConnection db;

	public ZhengReadDBTable(DBConnection db) {
		this.db = db;
	}

	public ZhengReadDBTable() {
		this.db = new DBConnection();
	}

	ResultSet rs = null;
	Statement stmt = null;
	Connection conn=null;
	/**
	 * ��ȡҽ�����е�ҽ��������ҽ��֢״
	 */

	public void getDoctorZhengData() {
		
		try {
			// ���ݿ����ӵĻ�ȡ
			Connection conn = db.getConn();
			stmt = conn.createStatement();
			// ���ݿ�sql����ƴ��
			String sql = "select id,name,cure_china_zheng from kb_doctor";
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
//				String line = id+";"+name;
//				FileUtil.appendData("d:\\doctor.txt",line);
				String cure_china_zheng= rs.getString("cure_china_zheng");
//				FileUtil.appendData("d:\\disease.txt",cure_china_disease);
				
				//�����һ�е�����ƴ���Էֺ����һ���ַ�����Ȼ��д��txt�ļ���
//				String line1 = id+";"+name+";"+cure_china_zheng;
//				FileUtil.appendData(ZhengDataSetConfig.AllDataPath, line1);
//				System.out.println(id+";"+name+";"+cure_china_disease);
				
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		db.close(rs);
		db.close(stmt);
		db.close(conn);

	}
}
