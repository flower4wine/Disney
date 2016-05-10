package com.disney.service;

import java.util.List;
import java.util.Map;

import com.disney.exception.JSApiException;
import com.disney.model.Park;

public interface ParkService {
	
	public Park find(String jsCode);
	
	/**
	 * 查询车辆所在停车场
	 * @param carNo
	 * @return
	 * @throws JSApiException
	 */
	public Park queryCarInPark(String carNo) throws JSApiException;
	
	/**
	 * 查询停车场
	 * @return
	 * @throws JSApiException
	 */
	public List<Map<String,Object>> queryParksInfo() throws JSApiException;
	
	/**
	 * 查询车辆停车详情
	 * @param carNo
	 * @return
	 * @throws JSApiException
	 */
	public Map<String,Object> queryCarParkInfo(String carNo) throws JSApiException;
	
	/**
	 * 查询订单
	 * @param carNo
	 * @return
	 * @throws JSApiException
	 */
	public Map<String,Object> queryOrder(String carNo) throws JSApiException;

}
