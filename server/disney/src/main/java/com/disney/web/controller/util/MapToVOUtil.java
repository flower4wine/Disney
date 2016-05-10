package com.disney.web.controller.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.disney.dao.LocationDao;
import com.disney.model.Location;
import com.disney.web.vo.jieshunapivo.QueryCarByCarnoVO;
import com.disney.web.vo.jieshunapivo.QueryOrderVO;
import com.disney.web.vo.jieshunapivo.QueryParkVO;

public class MapToVOUtil {
	
	@Autowired
	private static LocationDao lo;
	
	@SuppressWarnings("unchecked")
	public static List<QueryOrderVO> mapToQueryOrderVO(Map<String, Object> map){
		List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("dataItems");
		List<QueryOrderVO> voList = new ArrayList<QueryOrderVO>();
		for(Map<String, Object> car:list){
			Map<String,Object> attrs = (Map<String,Object>) car.get("attributes");
			QueryOrderVO vo = new QueryOrderVO();
			if(attrs.isEmpty() || attrs.size() <= 0){
				continue;
			}
			voList.add(vo);
			System.out.println(attrs);
		}
		return voList;
	}
	
	@SuppressWarnings("unchecked")
	public static List<QueryParkVO> mapToQueryParkVO(Map<String, Object> map){
		List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("dataItems");
		List<QueryParkVO> voList = new ArrayList<QueryParkVO>();
		for(Map<String, Object> park:list){
			Map<String,Object> attrs = (Map<String,Object>) park.get("attributes");
			QueryParkVO vo = new QueryParkVO();
			vo.setTotalSpace(Integer.valueOf(attrs.get("totalSpace").toString()));
			vo.setParkingName((String) attrs.get("parkName"));
			vo.setRestSpace(Integer.valueOf(attrs.get("restSpace").toString()));
			
			String parkCode = (String) attrs.get("parkCode");
			if(parkCode.equals("0000002236")){
				vo.setParkCode("03-0003");
			}
			voList.add(vo);
			System.out.println(attrs);
		}
		return voList;
	}
	
	@SuppressWarnings("unchecked")
	public static List<QueryCarByCarnoVO> mapToQueryCarByCarnoVO(Map<String, Object> map){
		List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("dataItems");
		List<QueryCarByCarnoVO> voList = new ArrayList<QueryCarByCarnoVO>();
		for(Map<String, Object> car:list){
			Map<String,Object> attrs = (Map<String,Object>) car.get("attributes");
			QueryCarByCarnoVO vo = new QueryCarByCarnoVO();
			if(attrs.isEmpty() || attrs.size() <= 0){
				continue;
			}
			String parkingCode = (String) attrs.get("parkPlaceCode");
			if(parkingCode.equals("0000002236")){
				vo.setParkingCode("03-0001");
			}
			Location location = lo.find(vo.getParkingCode());
			vo.setParkingName(location.getName());
			vo.setCarNo((String) attrs.get("carNo"));
			vo.setInParkingState(true);
			voList.add(vo);
			System.out.println(attrs);
		}
		return voList;
	}
	
}
