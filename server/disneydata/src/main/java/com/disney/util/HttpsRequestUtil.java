package com.disney.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonMappingException;



public class HttpsRequestUtil {
	
	private static Logger log = LoggerFactory.getLogger(HttpsRequestUtil.class);
	
	public static String METHOD_GET = "GET";
	public static String METHOD_POST = "POST";
	
	
	public static Map<String,Object> post(String requestUrl,String outputStr) {
		return httpsRequest(requestUrl,METHOD_POST,outputStr);
	}
	
	public static Map<String,Object> get(String requestUrl) {
		return httpsRequest(requestUrl,METHOD_GET,null);
	}
	
	
	public static String postString(String requestUrl,String outputStr) {
		return httpsRequestString(requestUrl,METHOD_POST,outputStr);
	}
	
	public static String getString(String requestUrl) {
		return httpsRequestString(requestUrl,METHOD_GET,null);
	}
	
	/**
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 */
	public static Map<String,Object> httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		String response = httpsRequestString(requestUrl, requestMethod, outputStr);
		
		if(StringUtils.isNotEmpty(response)){
			try {
				return JsonUtil.readJson2Map(response);
			} catch (JsonMappingException e) {
				log.error(""+e.getMessage());
			} catch (IOException e) {
				log.error(""+e.getMessage());
			}
		}
		
		return null;
	}
	
	
	public static String httpsRequestString(String requestUrl, String requestMethod, String outputStr) {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			@SuppressWarnings("restriction")
			URL url = new URL(null,requestUrl,new sun.net.www.protocol.https.Handler());
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();

			return buffer.toString();

		}catch (Exception e) {
			log.error(""+e.getMessage());
		}
		
		return null;
	}
}
