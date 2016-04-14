package com.disney.handler.wechat;

import java.util.Map;

public interface WeChatHandler {
	
	public String accessToken() throws Exception;
	public String jsTicket() throws Exception;
	public String appId();
	
	public String getWXBaseAuthUrl();
	
	public Map<String,Object> getAuthInfo(String code);
	
	public boolean isDebug();
	
	public String getDomain();
}
