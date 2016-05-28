package com.disney.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public static Map<String,Object>  post(String url,Map<String,Object> paramMap) {
		// 创建默认的httpClient实例.  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		// 创建httppost  
		HttpPost httppost = new HttpPost(url);
		
		// 创建参数队列  
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		for(String key:paramMap.keySet()){
			Object value = paramMap.get(key);
			
			if(value!=null){
				formparams.add(new BasicNameValuePair(key, value.toString()));
			}
		}
		
		
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String responseString = EntityUtils.toString(entity, "UTF-8");
					return JsonUtil.readJson2Map(responseString);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源  
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	/**
	 * 发送 get请求
	 */
	public static Map<String,Object> get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.  
			HttpGet httpget = new HttpGet(url);
			
			// 执行get请求.  
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体  
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// 打印响应内容长度     entity.getContentLength()
					// 打印响应内容  
					String responseString = EntityUtils.toString(entity);
					return JsonUtil.readJson2Map(responseString);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源  
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static void main(String args[]){
	}
}
