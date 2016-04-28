package com.disney.handler.jieshun;

import java.util.Map;

import com.disney.exception.JSApiException;

public interface JieShunService {

	public String getLoginToken(String cid, String user, String password,String version) throws JSApiException ;
	
	public Map<String,Object> queryParkSpace() throws JSApiException;
	
	/*public Map<String,Object> queryCarByCarno(JSApiRequestApiBO apiBO) throws JSApiException;

	public void queryCarInfoByCarno(JSApiRequestApiBO apiBO) throws JSApiException;

	public void payByCarno(JSApiRequestApiBO apiBO) throws JSApiException;*/
}
