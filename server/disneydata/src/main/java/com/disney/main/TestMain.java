package com.disney.main;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String prefix = "http://localhost:8080/disneydata";
		
		GenerateDataMain.generate(prefix,"/batchUpdateQrCode.html");
	}

}
