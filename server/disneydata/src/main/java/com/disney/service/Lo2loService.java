package com.disney.service;

import java.util.List;

import com.disney.bo.LoToLoBO;
import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;
import com.disney.model.LocationTimeDistance;

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
	
	/**
	 * 增加导览路线基础距离 时间数据
	 */
	/*public void saveLocationTimeDistance(String from,String to,Integer time,Integer distance);*/
	
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
