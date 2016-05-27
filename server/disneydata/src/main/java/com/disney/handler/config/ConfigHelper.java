package com.disney.handler.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ConfigHelper {
	
	private Properties attrs = new Properties();

	protected void loadProperties(String file){
		Properties p = null;
		try{

			p = new Properties();

			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(file);
			p.load(inputStream);


		} catch (IOException e1){
			e1.printStackTrace();
		}

		this.attrs =  p;
	}
	
	protected String value(String key){
		
		if(StringUtils.isNotEmpty(key) && !this.getAttrs().isEmpty()){
			return this.getAttrs().getProperty(key);
		}
		
		return null;
	}

	
	protected String value(String key,String defaultError){
		
		if(StringUtils.isNotEmpty(key) && !this.getAttrs().isEmpty()){
			return this.getAttrs().getProperty(key,defaultError);
		}
		
		return null;
	}

	private Properties getAttrs() {
		return attrs;
	}

}
