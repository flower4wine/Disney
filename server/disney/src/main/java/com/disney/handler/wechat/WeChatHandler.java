package com.disney.handler.wechat;

public interface WeChatHandler {
	
	public String accessToken() throws Exception;
	public String jsTicket() throws Exception;
	public String appId();

}
