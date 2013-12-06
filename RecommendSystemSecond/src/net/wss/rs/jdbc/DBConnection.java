package net.wss.rs.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	String dbDriver ="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String dbUrl="jdbc:sqlserver://localhost:1433;DatabaseName=RS";
	String dbName="sa";
	String dbPassword="sa";
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	

	/**
	 * 数据库的连接的获取操作
	 * @return conn 数据库连接
	 */
	public Connection getConn(){
		Connection conn= null;
		try {
			Class.forName(dbDriver);
			conn=DriverManager.getConnection(dbUrl,dbName,dbPassword);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return conn;
		
	}
	/**
	 * 数据的sql语句的预处理的操作
	 * @param conn
	 * @param sql
	 * @return ps
	 */
	public PreparedStatement getPre(Connection conn,String sql){
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return ps;
	}
	/**
	 * 数据库的conn的关闭的操作
	 * @param conn
	 */
	public void close(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
	/**
	 * 数据库的ps的关闭的操作
	 * @param ps
	 */
	public void close(PreparedStatement ps){
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
	/**
	 * 数据库的stmt的关闭操作
	 * @param stmt
	 */
	public void close(Statement stmt){
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
	/**
	 * 数据库的关闭结果集的操作
	 * @param rs
	 */
	public void close(ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	
	
	public PreparedStatement getPs() {
		return ps;
	}
	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}
	public ResultSet getRs() {
		return rs;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
}
