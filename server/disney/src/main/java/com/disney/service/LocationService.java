package com.disney.service;

import java.util.List;

import com.disney.bo.LoToLoBO;
import com.disney.model.FromToOptimize;
import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;
import com.disney.model.Location;
import com.disney.model.UserLocation;

public interface LocationService {
	
	public void cleanData();
	
	public List<Location> findAll();
	
	/**
	 * 保存
	 * @param location
	 * @return
	 */
	public void save(Location location);
	
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
	 * @param lo2lo
	 * @param out
	 * @param inner
	 */
	public void saveLo2Lo(LoToLo lo2lo,List<LoToLoStep> steps);
	
	/**
	 * 增加二维码
	 * @param code
	 * @param name
	 * @param qrCodeType
	 */
	public void addQrCode(String code, String name,Integer qrCodeType);
	
	/**
	 * 停车场 到 景点 可导览最优路径
	 * @param from
	 * @param to
	 */
	public void addFromTo(String from,String to);
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public FromToOptimize getFromTo(String from,String to);
	
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
