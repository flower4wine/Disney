package com.disney.service;

import java.util.List;

import com.disney.model.Location;
import com.disney.model.QrCode;
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

	/**
	 * 根据场景二维码URL 查找二维码
	 * @param qrCodeUrl
	 * @return
	 */
	public QrCode findQrCode(String qrCodeUrl);
	
	/**
	 * 根据场景二维码code 查找二维码
	 * @param code
	 * @return
	 */
	public QrCode findQrCodeByCode(String code);
	
	
	
}
