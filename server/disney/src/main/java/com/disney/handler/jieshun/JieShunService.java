package com.disney.handler.jieshun;

import java.util.Map;

import com.disney.exception.JSApiException;

public interface JieShunService {

	public String getLoginToken(String cid, String user, String password,String version) throws JSApiException ;
	
	public Map<String,Object> queryParkSpace() throws JSApiException;
	
	public Map<String,Object> queryCarStopByCarno(String carNo) throws JSApiException;
	
	public Map<String,Object> queryCarInfoByCarno(String carNo) throws JSApiException;

	public Map<String,Object> payByCarno(String carNo) throws JSApiException;
	
	public String createOrderByCarno(String carNo) throws JSApiException;
	
	public Map<String,Object> queryOrder(String orderNo) throws JSApiException;

	public Map<String, Object> queryOrderByCarNo(String carNo) throws JSApiException;
}
