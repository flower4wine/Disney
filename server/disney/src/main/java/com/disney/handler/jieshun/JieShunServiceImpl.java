package com.disney.handler.jieshun;

import org.springframework.stereotype.Service;

import com.disney.exception.JSApiException;

@Service
public class JieShunServiceImpl implements JieShunService{

	@Override
	public String getLoginToken(String cid, String user, String password,String version) throws JSApiException {
		
		String url = "http://preapi.jslife.net/jsaims/login";	
		return  JieShunHandler.getLoginToken(cid, user, password,version, url);
		
	}

}
