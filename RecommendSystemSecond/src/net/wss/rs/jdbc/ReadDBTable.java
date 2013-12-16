package net.wss.rs.jdbc;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.wss.rs.data.DataSetConfig;
import net.wss.rs.util.FileUtil;


public class ReadDBTable {
	DBConnection db;

	public ReadDBTable(DBConnection db) {
		this.db = db;
	}

	public ReadDBTable() {
		this.db = new DBConnection();
	}

	ResultSet rs = null;
	Statement stmt = null;
	Connection conn=null;
	
	
	public void getDoctorData() {
		
		try {
			// 数据库连接的获取
			Connection conn = db.getConn();
			stmt = conn.createStatement();
			// 数据库sql语句的拼接
			String sql = "select id,name,cure_china_disease from kb_doctor";
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
//				String line = id+";"+name;
//				FileUtil.appendData("d:\\doctor.txt",line);
//				String cure_china_disease= rs.getString("cure_china_disease");
//				FileUtil.appendData("d:\\disease.txt",cure_china_disease);
				
				//将获得一行的数据拼凑以分号相隔一个字符串，然后写到txt文件中
//				String line1 = id+";"+name+";"+cure_china_disease;
//				FileUtil.appendData(DataSetConfig.AllDataPath, lin1);
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

