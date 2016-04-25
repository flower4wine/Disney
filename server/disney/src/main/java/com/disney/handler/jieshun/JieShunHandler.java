package com.disney.handler.jieshun;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.disney.exception.JSApiException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JieShunHandler {
	
	public static String getLoginToken(String cid, String user, String password,String version,String url) throws JSApiException {

		String returnStr = null;

		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		CloseableHttpResponse response = null;
		
		try {

			ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

			list.add(new BasicNameValuePair("cid", cid));
			list.add(new BasicNameValuePair("usr", user));
			list.add(new BasicNameValuePair("psw", password));
			list.add(new BasicNameValuePair("v", version));

			HttpEntity en = new UrlEncodedFormEntity(list, "UTF-8");

			HttpUriRequest login = RequestBuilder.post().setUri(new URI(url))
					.setEntity(en).build();
			response = httpclient.execute(login);

			int statusCode=response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {

				String results = EntityUtils.toString(response.getEntity());
				JsonObject json=new JsonParser().parse(results).getAsJsonObject();
				
				int resultCode=json.get("resultCode").getAsInt();

				if(resultCode==0){
					returnStr =  json.get("token").getAsString();
				}else{
					
					throw new JSApiException("登录失败,错误信息：\tresultCode:"+resultCode+"\tmessage:"+json.get("message").getAsString());
				}

			} else {
				throw new JSApiException("调用失败，错误代码："+statusCode);
			}

		} catch (Exception e) {
			//Logger error
			throw new JSApiException("调用捷顺系统登录接口出错:"+e.getMessage());
			
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException ioe) {
				throw new JSApiException("调用捷顺系统登录接口出错:" + ioe.getMessage());
			}
		}
		
		return returnStr;
	}
}
