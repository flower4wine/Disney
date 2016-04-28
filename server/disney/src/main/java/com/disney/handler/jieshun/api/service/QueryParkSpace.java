package com.disney.handler.jieshun.api.service;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.disney.exception.JSApiException;
import com.disney.handler.jieshun.api.ApiHandler;
import com.disney.handler.jieshun.api.JSApiRequestApiBO;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class QueryParkSpace extends ApiHandler                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      {

	protected String buildRequestParam(JSApiRequestApiBO apiBO) {

		JsonObject jsonParam = new JsonObject();
		jsonParam.addProperty("serviceId", apiBO.getServiceId());
		jsonParam.addProperty("requestType", apiBO.getRequestType());
		JsonObject attributes = new JsonObject();
		Map<String, String> attrs = apiBO.getAttrs();
		attributes.addProperty("parkCodes", attrs.get("parkCodes"));
		jsonParam.add("attributes", attributes);
		return jsonParam.toString();
	}

	protected void extractResult(CloseableHttpResponse response)
			throws Exception,JSApiException {
		// TODO Auto-generated method stub
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {//成功调用
			System.out.println("queryparkspace-SUCCESS:接口调用成功!");
			String results = EntityUtils.toString(response.getEntity());
			JsonObject json=new JsonParser().parse(results).getAsJsonObject();
			int resultCode=json.get("resultCode").getAsInt();
			if(resultCode==0){
				JsonElement dataItems=json.get("dataItems");
				System.out.println("queryorder-SUCCESS:车场空车位获取成功!\n\t车场空车位信息："+dataItems.toString());
				
			}else{
				throw new JSApiException("queryparkspace-ERROR:调用异常!"+"\tresultCode:"+resultCode+"\tmessage:"+json.get("message"));
			}
		} else {
			throw new JSApiException("queryparkspace-ERROR:执行失败！"+"\tstatusCode:"+statusCode);

		}
	}

}
