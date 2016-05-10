package com.disney.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disney.dao.ParkDao;
import com.disney.exception.JSApiException;
import com.disney.handler.jieshun.JieShunService;
import com.disney.model.Park;
import com.disney.service.ParkService;

@Transactional
@Service
public class ParkServiceImpl implements ParkService {
	
	@Autowired
	private ParkDao parkDao;
	
	@Autowired
	private JieShunService jieShunService;

	@Override
	public Park find(String jsCode) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsCode", jsCode);
		
		return parkDao.getUniqueObjectWithAttrMap(map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Park queryCarInPark(String carNo) throws JSApiException {
		Map<String, Object> queryCarStopByCarno = jieShunService.queryCarStopByCarno(carNo);
		List<Map<String,Object>> list = (List<Map<String, Object>>) queryCarStopByCarno.get("dataItems");
		Park park = new Park();
		for (Map<String, Object> map : list) {
			Map<String,Object> attrs = (Map<String,Object>) map.get("attributes");
			park.setJsCode((String) attrs.get("parkCode"));
			park =parkDao.findByJsCode(park.getJsCode());
		}
		return park;
	}
	


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryParksInfo() throws JSApiException {
		Map<String, Object> queryParkSpace = jieShunService.queryParkSpace();
		List<Map<String,Object>> list = (List<Map<String, Object>>) queryParkSpace.get("dataItems");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryCarParkInfo(String carNo) throws JSApiException {
		Map<String, Object> queryCarStopByCarno = jieShunService.queryCarStopByCarno(carNo);
		List<Map<String,Object>> list = (List<Map<String, Object>>) queryCarStopByCarno.get("dataItems");
		Map<String, Object> attrs = new HashMap<String, Object>();
		for (Map<String, Object> map : list) {
			attrs = (Map<String,Object>) map.get("attributes");
		}
		return attrs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryOrder(String carNo) throws JSApiException {
		Map<String, Object> queryOrderByCarNo = jieShunService.queryOrderByCarNo(carNo);
		List<Map<String,Object>> list = (List<Map<String, Object>>) queryOrderByCarNo.get("dataItems");
		Map<String, Object> attrs = new HashMap<String, Object>();
		for (Map<String, Object> map : list) {
			attrs = (Map<String,Object>) map.get("attributes");
		}
		return attrs;
	}
}
