package com.disney.service;

import java.util.List;

import com.disney.bo.LoToLoBO;
import com.disney.model.Location;
import com.disney.model.UserLocation;

public interface LocationService {
	
	public List<Location> findAll();
	
	/**
	 * 查询位置
	 * @param qrCode
	 * @return
	 */
	public Location find(String qrCode);
	
	
	/**
	 * 加载导览路线
	 * @param from
	 * @param to
	 * @return
	 */
	public LoToLoBO loadLoToLoBO(String from,String to);
	
	
	/**
	 * 存储导览路线
	 * @param bo
	 */
	public void saveLoToLoBO(LoToLoBO bo);
	
	/**
	 * 增加二维码
	 * @param code
	 * @param name
	 */
	public void addQrCode(String code,String name,boolean isInout);
	
	/**
	 * 停车场 到 景点 可导览最优路径
	 * @param from
	 * @param to
	 */
	public void addFromTo(String from,String to);
	
	/**
	 * 
	 * @param userOpenId
	 * @return
	 */
	public UserLocation findUserLocation(String userOpenId);
	
	/**
	 * 
	 * @param userLocation
	 */
	public void saveUserLocation(UserLocation userLocation);
	
}
