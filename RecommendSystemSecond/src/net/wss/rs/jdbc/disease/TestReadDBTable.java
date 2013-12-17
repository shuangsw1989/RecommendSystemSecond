package net.wss.rs.jdbc.disease;

public class TestReadDBTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ReadDBTable rdtable= new ReadDBTable();
		System.out.println("将数据库存到到本地的txt文件中");
		rdtable.getDoctorDiseaseData();

	}

}
