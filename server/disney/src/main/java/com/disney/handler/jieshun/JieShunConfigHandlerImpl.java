package com.disney.handler.jieshun;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.disney.handler.config.ConfigHelper;

@Service
public class JieShunConfigHandlerImpl extends ConfigHelper implements JieShunConfigHandler {
	
	@PostConstruct  
	public void init() throws Exception {
		loadProperties("jieshun.properties");
	}

	@Override
	public String getConfigValue(String key) {
		return value(key);
	}

}
