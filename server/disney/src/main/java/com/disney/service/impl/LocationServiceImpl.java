package com.disney.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disney.bo.QrCodeBO;
import com.disney.dao.LocationDao;
import com.disney.dao.QrCodeDao;
import com.disney.dao.UserLocationDao;
import com.disney.model.Location;
import com.disney.model.QrCode;
import com.disney.model.UserLocation;
import com.disney.service.LocationService;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDao;


	@Autowired
	private QrCodeDao qrCodeDao;


	@Autowired
	private UserLocationDao userLocationDao;
	
	/**
	 * 缓存查询结果
	 */
	private Map<String,QrCodeBO> qrCodeMap = new HashMap<String,QrCodeBO>();
	
	/**
	 * 缓存位置
	 */
	private Map<String,Location> locationMap = new HashMap<String,Location>();
	
	@Override
	public Location find(String code) {
		
		if(locationMap.containsKey(code)){
			return locationMap.get(code);
		}
		
		Location loc = locationDao.find(code);
		
		if(loc!=null){
			locationMap.put(code, loc);
		}
		
		return loc;
	}
	
	
	@Override
	public QrCodeBO queryQrCodeInfo(String code) {
		
		if(qrCodeMap.containsKey(code)){
			return qrCodeMap.get(code);
		}
		
		
		QrCode qrCode = qrCodeDao.find(code);
		
		if(qrCode==null){
			return null;
		}
		
		Location loc = find(code);
		
		if(loc==null){
			return null;
		}
		
		QrCodeBO bo = new QrCodeBO();
		
		bo.setQrode(code);
		bo.setQrLocationName(loc.getName());
		bo.setRegion(qrCode.getRegion());
		bo.setCodeRange(qrCode.getCodeRange());
		bo.setLocationImg(loc.getLocationImg());
		
		qrCodeMap.put(code, bo);
		
		return bo;
	}

	@Override
	public UserLocation findUserLocation(String userOpenId) {
		return userLocationDao.find(userOpenId);
	}

	@Override
	public void saveUserLocation(UserLocation userLocation) {
		userLocationDao.saveOrUpdate(userLocation);
	}

	@Override
	public QrCode findQrCode(String qrCodeUrl) {
		return qrCodeDao.findByUrl(qrCodeUrl);
	}


	@Override
	public QrCode findQrCodeByCode(String code) {
		return qrCodeDao.find(code);
	}
}
