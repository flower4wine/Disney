package com.disney.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

public class WeChatCreateQrCodeUtil {
	
	public static String URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";
	//String createQrCodeUrl = String.format(URL, "");
	
	public static boolean createQrCode(String accessToken) throws JsonProcessingException {
		
		boolean result = false;
		
		String url =  String.format(URL, accessToken);
		
		Map<String,Object> param = new HashMap<String,Object>();
		
		//{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_str": "123"}}}
		
		param.put("action_name", "QR_LIMIT_SCENE");
		
		Map<String,Object> actionInfo = new HashMap<String,Object>();
		
		Map<String,Object> sence = new HashMap<String,Object>();
		
		sence.put("scene_str", "01-0001-0001");
		sence.put("scene_id ", 1);
		
		actionInfo.put("scene", sence);
		
		param.put("action_info", actionInfo);
		
		
		// 将菜单对象转换成json字符串
		String paramString = JsonUtil.objectToStringJson(param);

		// 发起POST请求创建菜单
		Map<String,Object> jsonObject = HttpsRequestUtil.httpsRequest(url, "POST", paramString);

		if (null != jsonObject) {
			result = true;
			System.out.println(jsonObject);
		}

		return result;
	}
	
	public static void main(String[] args){
		String accessToken = "lMitpPjgN5dbgSedZvgnyks92zodhyNHv0n8m0NPXna1J-E1zYdEZTDNPAqX1pXDIkfcoBhoXaIt-RNFAxnukDwIFfj0l06jNR00uw9cYAJyKA6yYfkglImAFepPegjETFRcACAIUG";
		
		try {
			
			createQrCode(accessToken);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
