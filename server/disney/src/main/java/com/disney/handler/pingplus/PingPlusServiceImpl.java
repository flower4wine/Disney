package com.disney.handler.pingplus;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.disney.bo.pingplus.PingOneBO;
import com.disney.bo.pingplus.PingPlusBO;
import com.disney.handler.config.ConfigHelper;
import com.pingplusplus.Pingpp;

@Service
public class PingPlusServiceImpl extends ConfigHelper implements PingPlusService {

	
	private PingPlusBO bo = null; 
	private PingOneBO one = null;

	@PostConstruct  
	public void init() {
		loadProperties("pingplus.properties");
		
		PingPlusBO bo = new PingPlusBO();

		bo = new PingPlusBO();

		Boolean isLive = new Boolean(value("pingplus.live"));
		bo.setLive(isLive);

		bo.setAppId(value("pingplus.appid"));

		bo.setTestSecret(value("pingplus.test.secret"));
		bo.setLiveSecret(value("pingplus.live.secret"));

		bo.setTestPublicKey(value("pingplus.test.publickey"));
		bo.setLivePublicKey(value("pingplus.live.publickey"));

		if(bo.getLive()){
			Pingpp.apiKey = bo.getLiveSecret();
		}else{
			Pingpp.apiKey = bo.getTestSecret();
		}
		
		this.bo = bo;
		
		
		PingOneBO one = new PingOneBO();

		one.setDomain(value("pingone.domain"));

		one.setChargeUrl(one.getDomain()+value("pingone.charge.url"));
		one.setSuccessUrl(one.getDomain()+value("pingone.success.url"));
		one.setAliPaySuccessUrl(one.getDomain()+value("pingone.aliwap.success.url"));
		
		this.one = one;
	}

	@Override
	public PingOneBO getOneBO() {
		return this.one; 
	}
	
	@Override
	public PingPlusBO getConfig() {
		return this.bo;
	}
}
