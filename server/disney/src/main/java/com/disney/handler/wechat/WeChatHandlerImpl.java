package com.disney.handler.wechat;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.disney.handler.config.ConfigHelper;
import com.disney.util.WeChatAuthUtil;
import com.disney.util.WeChatBaseUtil;

@Service
public class WeChatHandlerImpl extends ConfigHelper implements WeChatHandler {

	private WxTokenGenerate generator = null;
	private WxChatConfigBO config;
	private String domain;
	

	@PostConstruct  
	public void init() throws Exception {
		loadProperties("weixin.properties");

		this.config = loadConfig();
		this.generator = loadGenerator(this.config);
		this.domain = value("weixin.domain");

	}
	
	
	public String getDomain(){
		return this.domain;
	}

	private boolean isNeedReset(WxTokenGenerate tor){

		if(tor==null){
			return true;
		}

		if(!tor.isGenerate()){
			return true;
		}

		long time = System.currentTimeMillis() - tor.getGenerateTime();

		if(time > tor.getExpiresIn()){
			return true;
		}

		return false;
	}


	private WxTokenGenerate loadGenerator(WxChatConfigBO config) throws Exception{
		WxTokenGenerate generate = new WxTokenGenerate();

		Map<String,Object> map = WeChatBaseUtil.getToken(config.getAppId(),config.getAppSecret());

		if(map!=null){
			String accessToken = (String) map.get("access_token");
			Integer exp = (Integer) map.get("expires_in");

			generate.setAccessToken(accessToken);
			generate.setExpiresIn(exp-200);


			map = WeChatBaseUtil.getJsapiTicket(accessToken);

			if(map!=null){
				generate.setJsTicket((String) map.get("ticket"));
				generate.setGenerate(true);
				generate.setGenerateTime(System.currentTimeMillis());
			}
		}
		return generate;
	}

	private WxChatConfigBO loadConfig(){
		WxChatConfigBO bo = new WxChatConfigBO();

		bo.setAppId(value("weixin.appId"));
		bo.setAppSecret(value("weixin.secret"));
		bo.setAuthRedirectUrl(value("weixin.authRedirectUrl"));
		bo.setDebug(new Boolean(value("weixin.debug")));

		return bo;
	}


	@Override
	public String accessToken() throws Exception {
		WxTokenGenerate tor = this.generator;

		if(isNeedReset(tor)){
			this.generator = loadGenerator(this.config);
		}


		return tor.getAccessToken();
	}

	@Override
	public String jsTicket() throws Exception {
		WxTokenGenerate tor = this.generator;

		if(isNeedReset(tor)){
			this.generator = loadGenerator(this.config);
		}
		
		return tor.getJsTicket();
	}

	@Override
	public String appId() {
		String appId = this.config.getAppId();
		return appId;
	}
	
	/**
	 * 获取登录授权URL
	 */
	@Override
	public String getWXBaseAuthUrl(){
		return WeChatAuthUtil.getWXBaseAuthUrl(this.appId(),this.config.getAuthRedirectUrl());
	}
	
	
	/**
	 * 微信登录回调获取微信用户OpenId
	 */
	@Override
	public Map<String,Object> getAuthInfo(String code){
		return WeChatAuthUtil.getOauth2AccessToken(this.config.getAppId(), this.config.getAppSecret(), code);
	}

	@Override
	public boolean isDebug() {
		return this.config.isDebug();
	}

}
