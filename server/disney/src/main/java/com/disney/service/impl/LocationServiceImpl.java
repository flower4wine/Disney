package com.disney.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disney.bo.LoToLoBO;
import com.disney.bo.LoToLoStepBO;
import com.disney.bo.QrCodeBO;
import com.disney.constant.Lo2LoStepType;
import com.disney.dao.LoToLoDao;
import com.disney.dao.LoToLoStepDao;
import com.disney.dao.LocationDao;
import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;
import com.disney.model.Location;
import com.disney.service.LocationService;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private LoToLoDao loToLoDao;
	
	@Autowired
	private LoToLoStepDao loToLoStepDao;

	@Override
	public List<Location> findAll() {
		return locationDao.findAll();
	}
	
	@Override
	public LoToLoBO loadLoToLoBO(String from,String to){
		
		Location locFrom = locationDao.find(from);
		Location locTo = locationDao.find(to);
		
		LoToLo lo2lo = loToLoDao.find(from, to);
		
		if(lo2lo!=null && locFrom != null && locTo != null){
			LoToLoBO bo = new LoToLoBO();
			
			bo.setFrom(getQrCodeBO(locFrom.getName()));
			bo.setTo(getQrCodeBO(locTo.getName()));
			
			bo.setTime(lo2lo.getTime());
			bo.setDistince(lo2lo.getDistince());
			
			bo.setOutUrl(lo2lo.getInnerUrl());
			bo.setInnerUrl(lo2lo.getOutUrl());
			
			List<LoToLoStep>  ins = loToLoStepDao.find(lo2lo.getId(), Lo2LoStepType.IN);
			List<LoToLoStep>  outs = loToLoStepDao.find(lo2lo.getId(), Lo2LoStepType.OUT);
			
			bo.setInnerSteps(getStepList(ins));
			bo.setOutSteps(getStepList(outs));
			
			
			return bo;
		}
		
		return null;
	}

	
	private List<LoToLoStepBO> getStepList(List<LoToLoStep> lo2loStep){
		List<LoToLoStepBO> steps = new ArrayList<LoToLoStepBO>();
		if(lo2loStep!=null && lo2loStep.size()>0){
			for(LoToLoStep step : lo2loStep){
				LoToLoStepBO bo = new LoToLoStepBO();
				bo.setRemark(step.getRemark());
				steps.add(bo);
			}
		}
		return steps;
	}
	
	private QrCodeBO getQrCodeBO(String name){
		QrCodeBO bo = new QrCodeBO();
		bo.setQrLocationName(name);
		return bo;
	}
	
	@Override
	public Location find(String qrCode) {
		return locationDao.find(qrCode);
	}
	
}
