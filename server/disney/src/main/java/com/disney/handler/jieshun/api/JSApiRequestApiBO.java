package com.disney.handler.jieshun.api;

import java.util.HashMap;
import java.util.Map;

public class JSApiRequestApiBO {
	
	private String loginToken;
	
	private String serviceId;
	private String requestType;
	private Map<String,String> attrs = new HashMap<String,String>();
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public Map<String, String> getAttrs() {
		return attrs;
	}
	public void setAttrs(Map<String, String> attrs) {
		this.attrs = attrs;
	}

	public String getLoginToken() {
		return loginToken;
	}
	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}
	
	
	
}
