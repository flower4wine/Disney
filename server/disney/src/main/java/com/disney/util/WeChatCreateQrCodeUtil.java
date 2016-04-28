package com.disney.util;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

public class WeChatCreateQrCodeUtil {
	
	public static String URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";
	//String createQrCodeUrl = String.format(URL, "");
	
	public static String createQrCode(String accessToken,String sceneStr) throws JsonProcessingException {
		
		String result = "";
		
		String url =  String.format(URL, accessToken);
		
		//{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_str": "123"}}}
		// 将菜单对象转换成json字符串
		String paramString = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+sceneStr+"\"}}}";

		// 发起POST请求创建菜单
		Map<String,Object> jsonObject = HttpsRequestUtil.httpsRequest(url, "POST", paramString);

		if (null != jsonObject) {
			result = jsonObject.get("url").toString();
			System.out.println(result);
		}

		return result;
	}
	
	public static void main(String[] args) throws Exception{
		
	}

}
