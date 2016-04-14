package com.disney.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.disney.bo.LoToLoBO;
import com.disney.bo.LoToLoStepBO;
import com.disney.handler.config.SessionHelper;
import com.disney.model.Location;
import com.disney.model.UserLocation;
import com.disney.service.LocationService;
import com.disney.util.ViewUtil;
import com.disney.web.vo.GuideVO;
import com.disney.web.vo.LocationVO;

@Controller
@RequestMapping("/pg")
public class ParkGuideController {

	@Autowired
	private LocationService locationService;
	
	@RequestMapping("/lo")
	public ModelAndView parkLocation(HttpServletRequest request,String co) throws Exception {
		//Validate check error
		if(StringUtils.isEmpty(co) || co.length()!=12){
			//03-0001-0001 二维码格式不正确
			
		}
		
		Location parent = locationService.find(co.substring(0,7));
		Location child = locationService.find(co);
		
		String name = "/guide/location";
		ModelAndView view = ViewUtil.view(name);
		LocationVO vo = new LocationVO();
		
		if(parent==null || child==null){
			//位置信息不存在
			
			
		}else{
			vo.setCode(co);
			vo.setRemark(parent.getName());
			vo.setLocationImg(parent.getLocationImg());
		}

		view.addObject("location", vo);
		return view;
	}
	
	@RequestMapping("/loConfirm")
	public ModelAndView locationConfirm(HttpSession session, String code) throws Exception {
		
		String name = "redirect:/pg/toLocations.html";
		ModelAndView view = ViewUtil.view(name);
		
		String userOpenId = SessionHelper.getLoginUserOpenId(session);
		
		UserLocation ul = locationService.findUserLocation(userOpenId);
		
		if(ul==null){
			ul = new UserLocation();
			ul.setOpenId(userOpenId);
		}
		
		ul.setCreatedAt(new Date());
		ul.setParkLocation(code);
		
		locationService.saveUserLocation(ul);
		
		return view;
	}
	
	@RequestMapping("/toLocations")
	public ModelAndView outlets(HttpServletRequest request) throws Exception {
		String name = "/guide/outlets";
		ModelAndView view = ViewUtil.view(name);
		return view;
	}
	
	
	@RequestMapping("/toLocation")
	public ModelAndView outlet(HttpServletRequest request) throws Exception {
		String name = "/guide/toOutlet";
		ModelAndView view = ViewUtil.view(name);
		
		LoToLoBO bo = locationService.loadLoToLoBO("03-0001-0001", "01-0001-000A");
		view.addObject("guide", boToVo(bo));
		
		return view;
	}
	
	private GuideVO boToVo(LoToLoBO bo){
		GuideVO vo = new GuideVO();
		
		vo.setDestName(bo.getTo().getQrLocationName());
		
		
		List<LoToLoStepBO> steps = new ArrayList<LoToLoStepBO>();
				
		steps.addAll(bo.getInnerSteps());
		steps.addAll(bo.getOutSteps());
		
		for(LoToLoStepBO step:steps){
			vo.getSteps().add(step.getRemark());
		}
		
		vo.setTime(bo.getTime());
		vo.setDistince(bo.getDistince());
		vo.setOutPic(bo.getOutUrl());
		vo.setInnerPic(bo.getInnerUrl());
		
		return vo;
	}

}
