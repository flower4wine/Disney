package com.disney.service;

import java.util.List;

import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;
import com.disney.model.LocationTimeDistance;

public interface Lo2loService {
	
	
	
	/**
	 * 存储导览路线
	 * @param lo2lo
	 * @param out
	 * @param inner
	 */
	public void saveLo2Lo(LoToLo lo2lo,List<LoToLoStep> steps);
	
	
	/**
	 * 增加导览路线基础距离 时间数据
	 */
	public void saveLocationDistance(String from,String to,Integer distance);
	
	public void saveLocationDistance(String from, String to,Integer distance1,Integer distance2); 
	
	/**
	 * 获取路线时间 距离
	 * @param from
	 * @param to
	 * @return
	 */
	public LocationTimeDistance getLocationTimeDistance(String from,String to);

}
