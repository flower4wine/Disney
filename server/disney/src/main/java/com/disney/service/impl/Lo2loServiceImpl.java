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
import com.disney.constant.QrCodeType;
import com.disney.dao.LoToLoDao;
import com.disney.dao.LoToLoStepDao;
import com.disney.dao.LocationDao;
import com.disney.dao.QrCodeDao;
import com.disney.handler.entrance.EntranceHandler;
import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;
import com.disney.model.Location;
import com.disney.model.QrCode;
import com.disney.service.Lo2loService;

@Service
@Transactional
public class Lo2loServiceImpl implements Lo2loService {
	
	@Autowired
	private LocationDao locationDao;

	@Autowired
	private LoToLoDao loToLoDao;

	@Autowired
	private LoToLoStepDao loToLoStepDao;
	
	@Autowired
	private QrCodeDao qrCodeDao;
	
	@Autowired
	private EntranceHandler entranceHandler;
	
	private String loadStartCode(String from,String to){
		return entranceHandler.getEntrance(from, to);
	}

	@Override
	public LoToLoBO loadLoToLoBO(String from,String to){
		
		
		Location locFrom = locationDao.find(from);
		Location locTo = locationDao.find(to.substring(0,7));
		
		Location locToCode = locationDao.find(to);
		
		
		QrCode fromQrcode = qrCodeDao.find(from);
		QrCode toQrcode = qrCodeDao.find(to);
		
		
		//处理Leave Location 如果从停车场内部离开
		if(fromQrcode.getQrCodeType() == QrCodeType.PARK_INNER && toQrcode.getQrCodeType() == QrCodeType.PARK_INNER){
			from = loadStartCode(from,to);
		}

		LoToLo lo2lo = loToLoDao.find(from, to);

		if(lo2lo!=null && locFrom != null && locTo != null && locToCode != null){
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

			
			/*
			 * TODO 增加判断 是否忽略内部导览
			 * 1. 如果停车场内部 到 景点   且from 靠近停车场出入口  需要忽略内部导览
			 * 2. 如果景点到停车场内部    且to靠近停车场出入口 需要忽略内部导览
			 * 3. 如果停车场内部到停车场内部  如果to靠近停车场出入口  需要忽略内部导览
			 */
			if(fromQrcode.getQrCodeType() == QrCodeType.PARK_INNER && toQrcode.getQrCodeType() == QrCodeType.VIEW_ENTRANCE){
				if(fromQrcode.getNearEntra()){
					bo.setIgnoreInner(true);
				}
			}else{
				if(toQrcode.getNearEntra()){
					bo.setIgnoreInner(true);
				}
			}

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
	

}
