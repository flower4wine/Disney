package com.disney.handler.jieshun;

import com.disney.exception.JSApiException;
import com.google.gson.JsonObject;

public interface JieShunService {

	public String getLoginToken(String cid, String user, String password,String version) throws JSApiException ;
	
	public JsonObject queryParkSpace() throws JSApiException;
	
	public JsonObject queryCarByCarno() throws JSApiException;

	public void queryCarInfoByCarno() throws JSApiException;

	public void payByCarno() throws JSApiException;
}
