package com.disney.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;
import java.util.UUID;

import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonMappingException;

public class WeChatBaseUtil {
	
	private static Logger log = LoggerFactory.getLogger(WeChatBaseUtil.class);
	
	// 凭证获取（GET）
	public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
     * jsapi_ticket 接口地址
     */
	public static String JSAPI_TICKET_GET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

	/**
	 * 获取接口访问凭证
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws JsonMappingException 
	 */
	public static Map<String,Object> getToken(String appid, String appsecret) throws Exception {

		String requestUrl = TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		Map<String,Object> token = HttpsRequestUtil.get(requestUrl);
		return token;
	}
	
	
	/**
	 * 获取签名的时间戳
	 */
	public static String getTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取签名的随机串
	 */
	public static String getNonceStr() {
		return UUID.randomUUID().toString().replace("-","");
	}

	/**
	 * 获取jsapi_ticket  H5调用JS API服务
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws JsonMappingException
	 */
	public static Map<String, Object> getJsapiTicket(String accessToken) throws Exception {
		String getJsApiTokenUlr = String.format(JSAPI_TICKET_GET_URL, accessToken);
		Map<String,Object> token = HttpsRequestUtil.get(getJsApiTokenUlr);
		return token;
	}

	//字节转换16进制
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
	/**
	 * 获取签名 获取调用JSAPI的签名
	 * 
	 * @param noncestr
	 *            签名的随机串
	 * @param timestamp
	 *            签名的时间戳
	 * @param localUrl
	 *            当前网页的URL
	 * @return signature 签名
	 * @throws JSONException
	 */
	public static String getSignature(String noncestr, String timestamp, String jsApiTicket, String localUrl) {
		String result = "";
		String string1 = "jsapi_ticket=" + jsApiTicket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
				+ localUrl;

		try {
			
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			result = byteToHex(crypt.digest());
			
		} catch (NoSuchAlgorithmException e) {
			log.error(""+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.error(""+e.getMessage());
		}

		return result;
	}
	
	
	
	public static void main(String args[]) {
		// 获取接口访问凭证
		try {
			Map<String,Object> token = getToken("","");
			System.out.println(token);
			
			String accToken =(String)token.get("");
			System.out.println(accToken);

			String jsTick = (String) getJsapiTicket(accToken).get("");
			System.out.println(jsTick);
			
			
			//分享页面
			String url = "http://www.revitbus.com/revitbus/mobile/register/test.html";
			String noStr = "90653b21-e1ec-4665-b14c-513a2419287f";
			String time = "1444878159";
			
			//微信分享H5相关参数获取
			System.out.println(getSignature(noStr, time, jsTick, url));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
