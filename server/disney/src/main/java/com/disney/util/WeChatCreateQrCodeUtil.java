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
		}

		return result;
	}
	
	public static void main(String[] args) throws Exception{
		
		/*weixin.appId=wx7fd96db16363e281
		weixin.secret=268ebd1126a4cee787ba4de89ce9b945*/
		
		Map<String,Object> map = WeChatBaseUtil.getToken("wx7fd96db16363e281","268ebd1126a4cee787ba4de89ce9b945");
		String accessToken = (String) map.get("access_token");
		
		try {
			
			String a= createQrCode(accessToken,"01-0001-0001");
			String b= createQrCode(accessToken,"03-0001-0001");
			
			System.out.println(a);
			System.out.println(b);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
