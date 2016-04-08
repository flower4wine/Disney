package com.disney.handler.wechat;

public class WxChatConfigBO {
	
	private String appId;
	private String appSecret;
	private String authRedirectUrl;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getAuthRedirectUrl() {
		return authRedirectUrl;
	}
	public void setAuthRedirectUrl(String authRedirectUrl) {
		this.authRedirectUrl = authRedirectUrl;
	}
}
