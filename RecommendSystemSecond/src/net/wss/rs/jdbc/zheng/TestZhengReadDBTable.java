package net.wss.rs.jdbc.zheng;

public class TestZhengReadDBTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ZhengReadDBTable rdtable= new ZhengReadDBTable();
		System.out.println("将数据库存到到本地的txt文件中");
		rdtable.getDoctorZhengData();

	}

}
