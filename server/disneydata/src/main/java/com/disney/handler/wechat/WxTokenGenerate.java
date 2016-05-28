package com.disney.handler.wechat;

public class WxTokenGenerate {
	
	private String accessToken;
	private String jsTicket;
	
	private Integer expiresIn = 7000;
	private Long generateTime;
	
	private boolean reset = false;
	private boolean generate = false;
	
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getJsTicket() {
		return jsTicket;
	}
	public void setJsTicket(String jsTicket) {
		this.jsTicket = jsTicket;
	}
	public Long getGenerateTime() {
		return generateTime;
	}
	public void setGenerateTime(Long generateTime) {
		this.generateTime = generateTime;
	}
	public boolean isReset() {
		return reset;
	}
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	public boolean isGenerate() {
		return generate;
	}
	public void setGenerate(boolean generate) {
		this.generate = generate;
	}
}
