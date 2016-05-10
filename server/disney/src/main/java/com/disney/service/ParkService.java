package com.disney.service;

import java.util.List;
import java.util.Map;

import com.disney.exception.JSApiException;
import com.disney.model.Park;

public interface ParkService {
	
	public Park find(String jsCode);
	
	public Park queryCarInPark(String carNo) throws JSApiException;
	
	public List<Map<String,Object>> queryParksInfo() throws JSApiException;
	
	public Map<String,Object> queryCarParkInfo(String carNo) throws JSApiException;

}
