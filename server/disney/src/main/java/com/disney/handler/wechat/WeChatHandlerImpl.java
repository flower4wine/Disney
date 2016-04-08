package com.disney.handler.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.disney.util.WeChatBaseUtil;

@Service
public class WeChatHandlerImpl implements WeChatHandler {

	private WxTokenGenerate generator = null;

	private WxChatConfigBO config;


	@PostConstruct  
	public void init() throws Exception {
		Properties p = loadProperties("weixin.properties");

		this.config = loadConfig(p);
		this.generator = loadGenerator(this.config);

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

	private WxChatConfigBO loadConfig(Properties p){
		WxChatConfigBO bo = new WxChatConfigBO();

		bo.setAppId(p.getProperty("weixin.appId"));
		bo.setAppSecret(p.getProperty("weixin.secret"));

		return bo;
	}

	private Properties loadProperties(String file){
		Properties p = null;
		try{

			p = new Properties();

			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(file);
			p.load(inputStream);


		} catch (IOException e1){
			e1.printStackTrace();
		}

		return p;
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

}
