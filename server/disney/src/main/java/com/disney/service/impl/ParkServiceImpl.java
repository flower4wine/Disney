package com.disney.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disney.dao.ParkDao;
import com.disney.model.Park;
import com.disney.service.ParkService;

@Transactional
@Service
public class ParkServiceImpl implements ParkService {
	
	@Autowired
	private ParkDao parkDao;

	@Override
	public Park find(String jsCode) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsCode", jsCode);
		
		return parkDao.getUniqueObjectWithAttrMap(map);
	}

	@Override
	public Park queryCarInPark(String carNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> queryParksInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryCarParkInfo(String carNo) {
		// TODO Auto-generated method stub
		return null;
	}
}
