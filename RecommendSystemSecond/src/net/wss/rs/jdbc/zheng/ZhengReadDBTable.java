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
	 * 获取医生表当中的医生所治中医的症状
	 */

	public void getDoctorZhengData() {
		
		try {
			// 数据库连接的获取
			Connection conn = db.getConn();
			stmt = conn.createStatement();
			// 数据库sql语句的拼接
			String sql = "select id,name,cure_china_zheng from kb_doctor";
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
//				String line = id+";"+name;
//				FileUtil.appendData("d:\\doctor.txt",line);
				String cure_china_zheng= rs.getString("cure_china_zheng");
//				FileUtil.appendData("d:\\disease.txt",cure_china_disease);
				
				//将获得一行的数据拼凑以分号相隔一个字符串，然后写到txt文件中
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
