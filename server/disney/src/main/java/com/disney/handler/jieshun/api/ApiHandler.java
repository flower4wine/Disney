package com.disney.handler.jieshun.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.disney.exception.JSApiException;
import com.disney.util.JsonUtil;
import com.google.gson.JsonObject;

public class ApiHandler{    
	
	private static Logger log = LoggerFactory.getLogger(ApiHandler.class);

	private static  HttpEntity constructHttpEntity(String param,JSLoginBO loginBo)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		// 生成MD5签名
		MessageDigest md5Tool = MessageDigest.getInstance("MD5");
		byte[] md5Data = md5Tool.digest(param.getBytes("UTF-8"));
		String sn = Util.toHexString(md5Data);

		/*Properties prop = ConfigHelper.getProperties("public");*/
		
		ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("cid", loginBo.getCid()));
		list.add(new BasicNameValuePair("v", loginBo.getVersion()));
		list.add(new BasicNameValuePair("p", param));
		list.add(new BasicNameValuePair("sn", sn));// MD5特征码
		
		list.add(new BasicNameValuePair("tn",loginBo.getLoginToken()));// 取token
		
		HttpEntity en = new UrlEncodedFormEntity(list, "UTF-8");
		return en;
	}
	
	private static  String buildRequestParam(JSApiRequestApiBO apiBO) {
		// 构造请求参数对象
		JsonObject jsonParam = new JsonObject();
		jsonParam.addProperty("serviceId", apiBO.getServiceId());
		jsonParam.addProperty("requestType", apiBO.getRequestType());
		
		JsonObject attributes = new JsonObject();
		
		Map<String,String> attrs = apiBO.getAttrs();
		
		for(String key:attrs.keySet()){
			//加入对应参数
			attributes.addProperty(key, attrs.get(key));
		}
		
		jsonParam.add("attributes", attributes);
		
		return jsonParam.toString();
	}
	

	/**
	 * API执行方法，此方法是一个模板方法，子类无需实现
	 */
	public static JSApiResultBO execute(JSApiRequestApiBO apiBO,JSLoginBO loginBo,String url) throws JSApiException {
		
		/*String url = "http://preapi.jslife.net/jsaims/as";*/
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();
		CloseableHttpResponse response = null;
		
		try {
			// 发起请求
			HttpUriRequest requst = RequestBuilder.post().setUri(new URI(url))
					.setEntity(constructHttpEntity(buildRequestParam(apiBO),loginBo))
					.build();
			
			response = httpclient.execute(requst);
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {//成功调用
				
				String results = EntityUtils.toString(response.getEntity());
				
				log.debug(results);
				
				Map<String,Object> resultMap = JsonUtil.readJson2Map(results);
				int resultCode=(Integer) resultMap.get("resultCode");
				
				if(resultCode==0){
					return new JSApiResultBO(resultMap);
					
				}else{
					throw new JSApiException("调用捷顺API调用异常!"+"\tresultCode:"+resultCode+"\tmessage:"+resultMap.get("message"));
				}
			} else {
				throw new JSApiException("调用捷顺API执行失败！"+"\tstatusCode:"+statusCode);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return new JSApiResultBO(-1,"调用捷顺API执行失败！");
	}
	

}
