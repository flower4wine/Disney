package com.disney.handler.message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MessageHandlerImpl implements MessageHandler {
	
	
	private Properties allErrors;
	

	@PostConstruct  
	public void init() throws Exception {
		this.allErrors = loadProperties("error.properties");
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
	public String getErrorMessage(String errorCode) {
		String defaultError = "系统服务无法访问，请稍候重试。";
		
		String error = allErrors.getProperty("error."+errorCode, defaultError);
		
		if(StringUtils.isEmpty(error)){
			return defaultError;
		}
		
		return error;
		
	}

}
