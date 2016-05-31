package com.disney.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


	@Override
	public List<Location> findAll() {
		return locationDao.findAll();
	}


	@Override
	public Location find(String qrCode) {
		return locationDao.find(qrCode);
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
