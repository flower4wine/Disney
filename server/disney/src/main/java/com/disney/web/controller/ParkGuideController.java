package com.disney.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.disney.bo.LoToLoBO;
import com.disney.bo.LoToLoStepBO;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.service.LocationService;
import com.disney.util.Ajax;
import com.disney.util.ViewUtil;
import com.disney.util.WeChatBaseUtil;
import com.disney.web.vo.GuideVO;

@Controller
public class ParkGuideController {

	@Autowired
	private WeChatHandler weChatHandler;
	
	@Autowired
	private LocationService locationService;


	@RequestMapping("/parkGuide/scan")
	public ModelAndView scan(HttpServletRequest request) throws Exception {

		String name = "/guide/scan";

		String url =  request.getRequestURL().toString();
		String queryString = request.getQueryString();
		String localUrl = url + (StringUtils.isEmpty(queryString) ? "" : "?" + queryString);

		ModelAndView view = ViewUtil.view(name);
		

		String jsTicket =  weChatHandler.jsTicket();
		String nonceStr =  WeChatBaseUtil.getNonceStr();
		String timestamp = WeChatBaseUtil.getTimestamp();
		String signature = WeChatBaseUtil.getSignature(nonceStr, timestamp, jsTicket, localUrl);


		view.addObject("appId", weChatHandler.appId());
		view.addObject("timestamp", timestamp);
		view.addObject("nonceStr", nonceStr);
		view.addObject("signature", signature);
		

		return view;
	}
	
	
	@RequestMapping("/parkGuide/location")
	public ModelAndView parkLocation(HttpServletRequest request,String locationCode) throws Exception {

		String name = "/guide/location";
		ModelAndView view = ViewUtil.view(name);
		

		return view;
	}
	
	
	@RequestMapping("/parkGuide/outlets")
	public ModelAndView outlets(HttpServletRequest request) throws Exception {

		String name = "/guide/outlets";

		
		ModelAndView view = ViewUtil.view(name);
		
		

		return view;
	}
	
	
	@RequestMapping("/parkGuide/outletjson")
	@ResponseBody
	public Map<String,Object> json(){
		return Ajax.getSuccessReturnMapWithData(locationService.loadLoToLoBO("", ""));
	}
	
	
	@RequestMapping("/parkGuide/outlet")
	public ModelAndView outlet(HttpServletRequest request) throws Exception {
		String name = "/guide/toOutlet";
		ModelAndView view = ViewUtil.view(name);
		
		LoToLoBO bo = locationService.loadLoToLoBO("", "");
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
