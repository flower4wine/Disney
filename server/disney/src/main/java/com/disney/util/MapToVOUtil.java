package com.disney.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.disney.web.vo.ParkVO;
import com.disney.web.vo.jieshunvo.QueryOrderVO;

public class MapToVOUtil {

	public static QueryOrderVO mapToQueryOrderVO(Map<String, Object> map){
		QueryOrderVO vo = new QueryOrderVO();

		vo.setCarNo((String) map.get("carNo"));
		vo.setOrderNo((String) map.get("orderNo"));
		vo.setStartTime((String) map.get("startTime"));
		Integer serviceTime = Integer.valueOf(map.get("serviceTime").toString());
		vo.setServiceTime(DateUtils.secToTime(serviceTime));
		vo.setServiceFee(Double.valueOf(map.get("serviceFee").toString()));
		return vo;
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
