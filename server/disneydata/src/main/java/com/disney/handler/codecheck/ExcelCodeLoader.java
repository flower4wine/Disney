package com.disney.handler.codecheck;

import java.util.List;
import java.util.Map;

public interface ExcelCodeLoader {
	
	public Map<String,String> getQrCodeInfo(String code);
	
	List<Map<String,String>> getAllQrCode();
	

}
