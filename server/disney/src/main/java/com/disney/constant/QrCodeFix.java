package com.disney.constant;

import java.util.Map;

import com.disney.util.WeChatBaseUtil;
import com.disney.util.WeChatCreateQrCodeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

public class QrCodeFix {
	
	public static String LANDSCAPE_BRIDGE = "04-0005-000A";
	public static String LANDSCAPE_BRIDGE_QR_CODE_URL = "http://weixin.qq.com/q/PkQcuBvlwCO50poD8Wz3";
	
	public static void getUrl() throws Exception{
		
		Map<String,Object> map = WeChatBaseUtil.getToken("wxe5f6cefc92c343e6","f9e7063044526e7b88f1daa4a8e309ba");
		String accessToken = (String) map.get("access_token");
		
		try {
			
			String a= WeChatCreateQrCodeUtil.createQrCode(accessToken,QrCodeFix.LANDSCAPE_BRIDGE);
			System.out.println(a);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

}
