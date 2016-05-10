package com.disney.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.disney.web.vo.ParkVO;
import com.disney.web.vo.jieshunapivo.QueryCarByCarnoVO;
import com.disney.web.vo.jieshunapivo.QueryOrderVO;

public class MapToVOUtil {

	@SuppressWarnings("unchecked")
	public static QueryOrderVO mapToQueryOrderVO(Map<String, Object> map){

		Map<String,Object> order = (Map<String,Object>) map.get("attributes");

		QueryOrderVO vo = new QueryOrderVO();

		vo.setCarNo((String) order.get("carNo"));
		vo.setOrderNo((String) order.get("orderNo"));
		vo.setStartTime((String) order.get("startTime"));
		Integer serviceTime = Integer.valueOf(order.get("serviceTime").toString());
		vo.setServiceTime(DateUtils.secToTime(serviceTime));
		vo.setServiceFee(Double.valueOf(order.get("serviceFee").toString()));

		return vo;
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
			vo.setParkingCode((String) attrs.get("parkPlaceCode"));
			vo.setCarNo((String) attrs.get("carNo"));
			vo.setInParkingState(true);
			voList.add(vo);
			System.out.println(attrs);
		}
		return voList;
	}

	@SuppressWarnings("unchecked")
	public static List<ParkVO> mapToParkVO(List<Map<String, Object>> list){
		List<ParkVO> voList = new ArrayList<ParkVO>();
		for(Map<String, Object> map:list){
			Map<String,Object> attrs = (Map<String,Object>) map.get("attributes");
			System.out.println(attrs);
			ParkVO vo = new ParkVO();

			vo.setTotalSpace(Integer.valueOf(attrs.get("totalSpace").toString()));
			vo.setParkName((String) attrs.get("parkName"));
			vo.setRestSpace(Integer.valueOf(attrs.get("restSpace").toString()));
			vo.setJsCode((String) attrs.get("parkCode"));
			voList.add(vo);

		}
		return voList;
	}

}
