package com.disney.handler.jieshun;

import com.disney.exception.JSApiException;

public interface JieShunService {

	public String getLoginToken(String cid, String user, String password,String version) throws JSApiException ;
}
