package com.disney.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disney.constant.QrCodeType;
import com.disney.dao.FromToOptimizeDao;
import com.disney.dao.LoToLoDao;
import com.disney.dao.LoToLoStepDao;
import com.disney.dao.LocationDao;
import com.disney.dao.QrCodeDao;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.FromToOptimize;
import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;
import com.disney.model.Location;
import com.disney.model.QrCode;
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
	private WeChatHandler weChatHandler; 

	@Override
	public List<Location> findAll() {
		return locationDao.findAll();
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
				
				child.setLocationImg("/resources/img/location/"+code.substring(0,7)+"/"+code+".jpg");

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
			entity.setLine(fromTo.getLine());
			entity.setFromBus(fromTo.getFromBus());
			entity.setToBus(fromTo.getToBus());

			fromToOptimizeDao.save(entity);
		}

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

	/**
	 * 以From为开始
	 * 以To为结束的路线
	 */
	@Deprecated
	@Override
	public FromToOptimize getFromToStartWith(String from, String to) {
		return fromToOptimizeDao.findStartWith(from, to);
	}

	/**
	 * 以From为开始
	 * 以To为结束的路线
	 */
	@Override
	public FromToOptimize getFromToInfo(String from, String to) {
		return fromToOptimizeDao.find(from, to);
	}
	
	

	@Override
	public QrCode findQrCode(String qrCodeUrl) {
		return qrCodeDao.findByUrl(qrCodeUrl);
	}

	@Override
	public void updateQrCode(QrCode code) {
		//更新
		qrCodeDao.saveOrUpdate(code);
	}

	@Override
	public List<QrCode> allQrCodes() {
		//查询所有
		return qrCodeDao.findAll();
	}

	@Override
	public void update(Location location) {
		locationDao.update(location);
	}


	@Override
	public QrCode findQrCodeByCode(String code) {
		return qrCodeDao.find(code);
	}


}
