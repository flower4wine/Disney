package com.disney.service;

import com.disney.bo.LoToLoBO;

public interface Lo2loService {
	
	/**
	 * 加载导览路线
	 * @param from
	 * @param to
	 * @return
	 */
	public LoToLoBO loadLoToLoBO(String from,String to);
	
	

}
