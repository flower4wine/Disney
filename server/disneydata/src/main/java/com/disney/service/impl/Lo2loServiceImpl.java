package com.disney.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disney.dao.LoToLoDao;
import com.disney.dao.LoToLoStepDao;
import com.disney.dao.LocationTimeDistanceDao;
import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;
import com.disney.model.LocationTimeDistance;
import com.disney.service.Lo2loService;

@Service
@Transactional
public class Lo2loServiceImpl implements Lo2loService {
	

	@Autowired
	private LoToLoDao loToLoDao;

	@Autowired
	private LoToLoStepDao loToLoStepDao;
	
	
	@Autowired
	private LocationTimeDistanceDao locationTimeDistanceDao;
	


	@Override
	public void saveLo2Lo(LoToLo lo2lo, List<LoToLoStep> steps) {

		LoToLo orgin = loToLoDao.find(lo2lo.getFromQrCode(), lo2lo.getToQrCode());

		if(orgin!=null){
			return;
		}

		loToLoDao.save(lo2lo);

		for(LoToLoStep step:steps){
			step.setLoToLoId(lo2lo.getId());
			loToLoStepDao.save(step);
		}

	}

	private void saveLocationTimeDistance(String from, String to, Integer time,Integer distance) {
		
		if(getLocationTimeDistance(from, to)!=null){
			return;
		}
		
		LocationTimeDistance ltd = new LocationTimeDistance();
		
		ltd.setFromCode(from);
		ltd.setToCode(to);
		ltd.setTime(time);
		ltd.setDistance(distance);
		
		locationTimeDistanceDao.save(ltd);
		
	}
	
	@Override
	public void saveLocationDistance(String from, String to,Integer distance) {
		saveLocationDistance(from,to,0,distance);
		
	}
	
	@Override
	public void saveLocationDistance(String from, String to,Integer distance1,Integer distance2) {
		float rateWalk = 60;
		float rateBus = 350;
		
		int min1 = Math.round(distance1/rateBus);
		int min2 = Math.round(distance2/rateWalk);
		
		if(distance1>0 && min1==0){
			min1=1;
		}
		
		if(distance2>0 && min2==0){
			min2=1;
		}
		
		int time = min1 + min2;
		
		saveLocationTimeDistance(from,to,time,distance1+distance2);
	}
	
	@Override
	public LocationTimeDistance getLocationTimeDistance(String from, String to) {
		return locationTimeDistanceDao.getLocationTimeDistance(from, to);
	}


}
