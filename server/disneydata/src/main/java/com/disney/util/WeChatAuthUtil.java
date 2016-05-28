package com.disney.util;

import java.util.Map;

public class WeChatAuthUtil {
	
	//private static Logger log = LoggerFactory.getLogger(WeChatAuthUtil.class);

	/**
	 * 获取网页授权凭证
	 * 
	 * @param appId 公众账号的唯一标识
	 * @param appSecret 公众账号的密钥
	 * @param code
	 * @return WeixinAouth2Token
	 */
	public static Map<String,Object> getOauth2AccessToken(String appId, String appSecret, String code) {
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		// 获取网页授权凭证
		Map<String,Object> jsonObject = HttpsRequestUtil.httpsRequest(requestUrl, "GET", null);
		return jsonObject;
	}

	/*
	 * 刷新网页授权凭证
	 * 
	 * @param appId 公众账号的唯一标识
	 * @param refreshToken
	 * @return WeixinAouth2Token
	 */
	public static Map<String,Object> refreshOauth2AccessToken(String appId, String refreshToken) {
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
		// 刷新网页授权凭证
		Map<String,Object> jsonObject = HttpsRequestUtil.httpsRequest(requestUrl, "GET", null);
		return jsonObject;
	}
	
	public static String WX_BASE_AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
			"appid=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s#wechat_redirect";
	
	
	public static String getWXBaseAuthUrl(String appId,String redirectUrl){
		
		String url = String.format(WX_BASE_AUTH_URL,
				appId, 
                redirectUrl, 
                "code", 
                "snsapi_base", "STATE");
		return url;
	}

}
