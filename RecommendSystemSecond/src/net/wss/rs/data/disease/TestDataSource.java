package net.wss.rs.data.disease;

public class TestDataSource {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataSourceConv getDs = new DataSourceConv();
		getDs.readAllDataTxtFile(DataSetConfig.AllDataPath);
	}

}
