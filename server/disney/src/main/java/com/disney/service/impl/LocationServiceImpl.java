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

	@Override
	public void saveLoToLoBO(LoToLoBO bo) {
		QrCodeBO locationTo = bo.getTo();
		String to = locationTo.getQrode();
		
		QrCodeBO locationFrom = bo.getFrom();
		String from = locationFrom.getQrode();
		
		Location locFrom = locationDao.find(from);
		Location locTo = locationDao.find(to);
		
		LoToLo lo2lo = loToLoDao.find(from, to);
		
		if(locTo == null){
			Location locToNew = new Location();
			locToNew.setLocation(to);
			locToNew.setName(locationTo.getQrLocationName());
			Integer type = Integer.valueOf(to.substring(to.indexOf("0"),to.indexOf("-")));
			locToNew.setType(type);
			locToNew.setQrCodeLocation(true);
			locationDao.save(locToNew);
		}
		
		if(locFrom == null){
			Location locFromNew = new Location();
			locFromNew.setLocation(from);
			locFromNew.setName(locationFrom.getQrLocationName());
			Integer type = Integer.valueOf(from.substring(from.indexOf("0"),from.indexOf("-")));
			locFromNew.setType(type);
			locFromNew.setQrCodeLocation(true);
			locationDao.save(locFromNew);
		}
		
		if(lo2lo == null){
			LoToLo lo2loNew = new LoToLo();
			lo2loNew.setToQrCode(to);
			lo2loNew.setFromQrCode(from);
			lo2loNew.setInnerUrl(bo.getInnerUrl());
			lo2loNew.setOutUrl(bo.getOutUrl());
			lo2loNew.setTime(bo.getTime());
			lo2loNew.setDistince(bo.getDistince());
			loToLoDao.save(lo2loNew);
			
			Long loToLoId = lo2loNew.getId();
			LoToLoStep lo2lostep = new LoToLoStep();
			lo2lostep.setLoToLoId(loToLoId);	
			
			List<LoToLoStepBO> innerSteps = bo.getInnerSteps();
			for (LoToLoStepBO loToLoStepBO : innerSteps) {
				lo2lostep.setType(Lo2LoStepType.IN);
				lo2lostep.setStep(loToLoStepBO.getStep());
				Long locationId = locationDao.find(loToLoStepBO.getLocation()).getId();
				lo2lostep.setLocationId(locationId);
				lo2lostep.setRemark(loToLoStepBO.getRemark());
				loToLoStepDao.save(lo2lostep);
			}
			
			List<LoToLoStepBO> outSteps = bo.getOutSteps();
			for (LoToLoStepBO loToLoStepBO : outSteps) {
				lo2lostep.setType(Lo2LoStepType.OUT);
				lo2lostep.setStep(loToLoStepBO.getStep());
				Long locationId = locationDao.find(loToLoStepBO.getLocation()).getId();
				lo2lostep.setLocationId(locationId);
				lo2lostep.setRemark(loToLoStepBO.getRemark());
				loToLoStepDao.save(lo2lostep);
			}
			
		}
		
	}
	
}
