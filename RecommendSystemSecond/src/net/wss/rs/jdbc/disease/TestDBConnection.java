package net.wss.rs.jdbc.disease;

import junit.framework.TestCase;
import net.wss.rs.jdbc.DBConnection;


public class TestDBConnection extends TestCase{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testDBConnection();//�������ݿ�����
		testCloseDB();//�������ݿ�Ĺر�

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
