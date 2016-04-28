package com.disney.handler.jieshun.api.service;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.disney.exception.JSApiException;
import com.disney.handler.jieshun.api.ApiHandler;
import com.disney.handler.jieshun.api.JSApiRequestApiBO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class QueryCarByCarno  extends ApiHandler{

	protected String buildRequestParam(JSApiRequestApiBO apiBO) {
		JsonObject jsonParam = new JsonObject();
		jsonParam.addProperty("serviceId", apiBO.getServiceId());
		jsonParam.addProperty("requestType", apiBO.getRequestType());
		JsonObject attributes = new JsonObject();
		Map<String, String> attrs = apiBO.getAttrs();
		attributes.addProperty("parkCode", attrs.get("parkCode"));
		attributes.addProperty("carNo", attrs.get("carNo"));
		attributes.addProperty("isCallBack", 0);
		jsonParam.add("attributes", attributes);

		return jsonParam.toString();
	}

	protected void extractResult(CloseableHttpResponse response) throws Exception,JSApiException {
		// TODO Auto-generated method stub
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {//成功调用
			String results = EntityUtils.toString(response.getEntity());
			JsonObject json=new JsonParser().parse(results).getAsJsonObject();
			int resultCode=json.get("resultCode").getAsInt();
			if(resultCode==0){
				System.out.println("querycarbycarno-SUCCESS:查询相似车辆成功!");
				JsonElement dataItems=json.get("dataItems");
				if(dataItems==null||(dataItems.isJsonArray() && dataItems.getAsJsonArray().size()==0)){
					System.out.println("querycarbycarno-SUCCESS:没有匹配的车辆！");
				}else{
					System.out.println("querycarbycarno-SUCCESS:匹配到的车辆如下：");
					JsonArray items=dataItems.getAsJsonArray();
					for(int i=0;i<items.size();i++){
						JsonObject door=items.get(i).getAsJsonObject().get("attributes").getAsJsonObject();
						System.out.println("\t<"+(i+1)+">carNo:"+door.get("carNo").getAsString()+"\tenterTime:"+door.get("enterTime").getAsString());
					}
				}
			}else{
				throw new JSApiException("querycarbycarno-ERROR:调用异常!"+"\tresultCode:"+resultCode+"\tmessage:"+json.get("message"));
			}
		} else {
			throw new JSApiException("querydoors-ERROR:执行失败！"+"\tstatusCode:"+statusCode);
		}
	}

}
