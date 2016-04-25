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
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.FromToOptimize;
import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;
import com.disney.model.Location;
import com.disney.model.QrCode;
import com.disney.model.UserLocation;
import com.disney.service.LocationService;
import com.disney.util.WeChatCreateQrCodeUtil;

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

	@Autowired
	private WeChatHandler weChatHandler; 

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

			bo.setOutUrl(lo2lo.getOutUrl());
			bo.setInnerUrl(lo2lo.getInnerUrl());

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
				bo.setStepType(step.getStepType());
				
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
				}else{
					//生成场景二维码地址
					try {
						String url = WeChatCreateQrCodeUtil.createQrCode(weChatHandler.accessToken(), code);
						qr.setQrUrl(url);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
	public void addFromTo(FromToOptimize fromTo) {

		if(fromToOptimizeDao.find(fromTo.getFromCode(), fromTo.getToCode()) == null){
			FromToOptimize entity = new FromToOptimize();

			entity.setFromCode(fromTo.getFromCode());
			entity.setToCode(fromTo.getToCode());
			entity.setBus(fromTo.getBus());
			entity.setInside(fromTo.getInside());
			entity.setFromBus(fromTo.getFromBus());
			entity.setToBus(fromTo.getToBus());

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

		for(LoToLoStep step : loToLoStepDao.findAll()){
			loToLoStepDao.delete(step);
		}

		for(LoToLo lo : loToLoDao.findAll()){
			loToLoDao.delete(lo);
		}

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

	@Override
	public QrCode findQrCode(String qrCodeUrl) {
		return qrCodeDao.findByUrl(qrCodeUrl);
	}
	
	

}
