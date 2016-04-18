package com.disney.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disney.bo.LoToLoBO;
import com.disney.bo.LoToLoStepBO;
import com.disney.bo.QrCodeBO;
import com.disney.constant.Lo2LoStepType;
import com.disney.constant.QrCodeType;
import com.disney.dao.FromToOptimizeDao;
import com.disney.dao.LoToLoDao;
import com.disney.dao.LoToLoStepDao;
import com.disney.dao.LocationDao;
import com.disney.dao.QrCodeDao;
import com.disney.dao.UserLocationDao;
import com.disney.model.FromToOptimize;
import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;
import com.disney.model.Location;
import com.disney.model.QrCode;
import com.disney.model.UserLocation;
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
	
	
	@Autowired
	private QrCodeDao qrCodeDao;
	
	@Autowired
	private FromToOptimizeDao fromToOptimizeDao;
	
	@Autowired
	private UserLocationDao userLocationDao;

	@Override
	public List<Location> findAll() {
		return locationDao.findAll();
	}
	
	@Override
	public LoToLoBO loadLoToLoBO(String from,String to){
		
		Location locFrom = locationDao.find(from);
		Location locTo = locationDao.find(to.substring(0,7));
		
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

	/*@Override
	public void saveLoToLoBO(LoToLoBO bo) {
		
		String from = bo.getFrom().getQrode();
		String to = bo.getTo().getQrode();
		
		Location f = locationDao.find(from);
		Location t = locationDao.find(to);
		
		LoToLo lo2lo = loToLoDao.find(from, to);
		
		if(lo2lo == null){
			lo2lo = new LoToLo();
			lo2lo.setToQrCode(to);
			lo2lo.setFromQrCode(from);
			
			lo2lo.setInnerUrl(bo.getInnerUrl());
			lo2lo.setOutUrl(bo.getOutUrl());
			
			lo2lo.setTime(bo.getTime());
			lo2lo.setDistince(bo.getDistince());
			loToLoDao.save(lo2lo);
		}else{
			return;
		}
		
		Long loToLoId = lo2lo.getId();
		
		LoToLoStep lo2lostep = null;
		
		List<LoToLoStepBO> innerSteps = bo.getInnerSteps();
		for (LoToLoStepBO loToLoStepBO : innerSteps) {
			
			lo2lostep = new LoToLoStep();
			lo2lostep.setLoToLoId(loToLoId);	
			
			lo2lostep.setType(Lo2LoStepType.IN);
			lo2lostep.setStep(loToLoStepBO.getStep());
			lo2lostep.setRemark(loToLoStepBO.getRemark());
			loToLoStepDao.save(lo2lostep);
		}
		
		List<LoToLoStepBO> outSteps = bo.getOutSteps();
		
		for (LoToLoStepBO loToLoStepBO : outSteps) {
			
			lo2lostep = new LoToLoStep();
			lo2lostep.setLoToLoId(loToLoId);	
			
			lo2lostep.setType(Lo2LoStepType.OUT);
			lo2lostep.setStep(loToLoStepBO.getStep());
			lo2lostep.setRemark(loToLoStepBO.getRemark());
			loToLoStepDao.save(lo2lostep);
		}
		
	}*/

	@Override
	public void addQrCode(String code, String name,Integer qrCodeType) {
		
		if(StringUtils.isNotEmpty(code) && code.length()==12 && qrCodeDao.find(code) == null){

			Location parent = locationDao.find(code.substring(0,7));
			
			if(parent != null){
				Location child = new Location();
				
				child.setLocation(code);
				child.setName(name);
				child.setQrCodeLocation(true);
				child.setType(parent.getType());
				
				locationDao.save(child);
				
				QrCode qr = new QrCode();
				
				qr.setLocationId(child.getId());
				qr.setQrCode(code);
				
				qr.setQrCodeType(qrCodeType);
				
				qr.setOrderNo(getOrderNo());
				
				if(qrCodeType == QrCodeType.PARK_ENTRANCE){
					qr.setQrUrl("");
				}else if(qrCodeType == QrCodeType.PARK_INNER){
					qr.setQrUrl("/pg/lo?co="+qr.getQrCode());
				}else{
					qr.setQrUrl("/le/lo?co="+qr.getQrCode());
				}
				
				qrCodeDao.save(qr);
			}
		}
	}
	
	private String getOrderNo(){
		int num = qrCodeDao.count()+1;
		String str = "0000"+num;
		return str.substring(str.length()-4, str.length());
	}

	@Override
	public void addFromTo(String from, String to) {
		
		if(fromToOptimizeDao.find(from, to) == null){
			FromToOptimize entity = new FromToOptimize();
			
			entity.setFromCode(from);
			entity.setToCode(to);
			
			fromToOptimizeDao.save(entity);
		}
		
	}

	@Override
	public UserLocation findUserLocation(String userOpenId) {
		return userLocationDao.find(userOpenId);
	}

	@Override
	public void saveUserLocation(UserLocation userLocation) {
		userLocationDao.saveOrUpdate(userLocation);
	}

	@Override
	public void save(Location location) {
		
		if(find(location.getLocation())==null){
			locationDao.saveOrUpdate(location);
		}
		
	}
	
	public void cleanData(){
		
		for(QrCode code:qrCodeDao.findAll()){
			qrCodeDao.delete(code);
		}
		
		for(Location location:locationDao.findAll()){
			locationDao.delete(location);
		}
		
		
	}

	@Override
	public FromToOptimize getFromTo(String from, String to) {
		return fromToOptimizeDao.find1(from, to);
	}

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
	
}
