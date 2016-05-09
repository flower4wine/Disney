package com.disney.handler.message;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.disney.handler.config.ConfigHelper;

@Service
public class MessageHandlerImpl extends ConfigHelper implements MessageHandler {

	@PostConstruct  
	public void init(){
		loadProperties("error.properties");
	}
	

	@Override
	public String getErrorMessage(String errorCode) {
		String defaultError = "系统服务无法访问，请稍候重试。";
		
		String error = value("error."+errorCode, defaultError);
		
		if(StringUtils.isEmpty(error)){
			return defaultError;
		}
		
		return error;
		
	}

}
