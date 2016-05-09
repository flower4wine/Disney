package com.disney.bo.pingplus;

public class PingPlusBO {
	
	private String appId;
	private String testSecret;
	private String liveSecret;
	
	private String testPublicKey;
	private String livePublicKey;
	
	private Boolean live = false;
	
	public Boolean getLive() {
		return live;
	}
	public void setLive(Boolean live) {
		this.live = live;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTestSecret() {
		return testSecret;
	}
	public void setTestSecret(String testSecret) {
		this.testSecret = testSecret;
	}
	public String getLiveSecret() {
		return liveSecret;
	}
	public void setLiveSecret(String liveSecret) {
		this.liveSecret = liveSecret;
	}
	public String getTestPublicKey() {
		return testPublicKey;
	}
	public void setTestPublicKey(String testPublicKey) {
		this.testPublicKey = testPublicKey;
	}
	public String getLivePublicKey() {
		return livePublicKey;
	}
	public void setLivePublicKey(String livePublicKey) {
		this.livePublicKey = livePublicKey;
	}
	
	
	
}
