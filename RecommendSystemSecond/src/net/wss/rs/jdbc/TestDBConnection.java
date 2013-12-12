package net.wss.rs.jdbc;

public class TestDBConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testDBConnection();//测试数据库连接
		testCloseDB();//测试数据库的关闭

	}

	public static void testDBConnection() {
		DBConnection db = new DBConnection();
		System.out.println(db.getConn());
	}

	public static void testCloseDB() {
		DBConnection db = new DBConnection();
		db.close(db.getConn());
	}
}
