package com.disney.service;

import java.util.List;

import com.disney.bo.LoToLoBO;
import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;

public interface Lo2loService {
	
	/**
	 * 加载导览路线
	 * @param from
	 * @param to
	 * @return
	 */
	public LoToLoBO loadLoToLoBO(String from,String to);
	
	
	/**
	 * 存储导览路线
	 * @param lo2lo
	 * @param out
	 * @param inner
	 */
	public void saveLo2Lo(LoToLo lo2lo,List<LoToLoStep> steps);

}
