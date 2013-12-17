package net.wss.rs.data.zheng;

public class TestZhengDataSourceConv {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ZhengDataSourceConv getDs = new ZhengDataSourceConv();
		getDs.readAllDataTxtFile(ZhengDataSetConfig.AllDataPath);

	}

}
