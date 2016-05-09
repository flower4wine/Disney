package com.disney.handler.pingplus;

import com.disney.bo.pingplus.PingOneBO;
import com.disney.bo.pingplus.PingPlusBO;

public interface PingPlusService {
	
	public PingPlusBO getConfig();
	public PingOneBO getOneBO();

}
