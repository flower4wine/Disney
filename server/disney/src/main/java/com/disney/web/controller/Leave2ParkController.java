package com.disney.web.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.disney.bo.LoToLoBO;
import com.disney.constant.Lo2LoStepType;
import com.disney.handler.config.SessionHelper;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.FromToOptimize;
import com.disney.model.Location;
import com.disney.model.UserLocation;
import com.disney.service.LocationService;
import com.disney.util.Ajax;
import com.disney.util.Base64Util;
import com.disney.util.DateUtils;
import com.disney.util.ViewUtil;
import com.disney.util.WeChatBaseUtil;
import com.disney.web.vo.GuideVO;
import com.disney.web.vo.LocationVO;

@Controller
@RequestMapping("/le")
public class Leave2ParkController {

	@Autowired
	private WeChatHandler weChatHandler;
	
	@Autowired
	private LocationService locationService;
	
	
	
	
	@RequestMapping("/lo")
	public ModelAndView parkLocation(HttpServletRequest request,String co) throws Exception {
		//Validate check error
		if(StringUtils.isEmpty(co) || co.length()!=12){
			//01-0001-0001 二维码格式不正确
		}
		
		Location parent = locationService.find(co.substring(0,7));
		Location child = locationService.find(co);
		
		String name = "/leave/location";
		ModelAndView view = ViewUtil.view(name);
		LocationVO vo = new LocationVO();
		
		if(parent==null || child==null){
			//位置信息不存在
			
			
		}else{
			vo.setCode(co);
			vo.setRemark(parent.getName());
			vo.setLocationImg(parent.getLocationImg());
		}

		//Get WeXin info
		String url =  weChatHandler.getDomain() + request.getRequestURI();  
		String queryString = request.getQueryString();
		String localUrl = url + (StringUtils.isEmpty(queryString) ? "" : "?" + queryString);
		String jsTicket =  weChatHandler.jsTicket();
		
		String nonceStr =  WeChatBaseUtil.getNonceStr();
		String timestamp = WeChatBaseUtil.getTimestamp();
		String signature = WeChatBaseUtil.getSignature(nonceStr, timestamp, jsTicket, localUrl);

		view.addObject("appId", weChatHandler.appId());
		view.addObject("timestamp", timestamp);
		view.addObject("nonceStr", nonceStr);
		view.addObject("signature", signature);
				
		view.addObject("location", vo);
		return view;
	}
	
	@RequestMapping("/loConfirm")
	public ModelAndView locationConfirm(HttpSession session, String code) throws Exception {
		String userOpenId = SessionHelper.getLoginUserOpenId(session);
		UserLocation ul = locationService.findUserLocation(userOpenId);
		
		if(ul==null){
			ul = new UserLocation();
			ul.setOpenId(userOpenId);
		}else{
			//过期清除原 停车地点
			Date now = DateUtils.getStartDate(new Date());
			if(DateUtils.getStartDate(ul.getCreatedAt()).compareTo(now) != 0){
				ul.setParkLocation("");
			}
		}
		ul.setCreatedAt(new Date());
		
		if(StringUtils.isNotEmpty(code) && code.length() == 12){
			ul.setLeaveLocation(code);
		}
		locationService.saveUserLocation(ul);
		
		String name = "redirect:/le/queryPark.html";
		
		if(StringUtils.isNotEmpty(ul.getParkLocation()) && ul.getParkLocation().length()==12){
			name = "redirect:/le/toLocation.html";
		}
		
		ModelAndView view = ViewUtil.view(name);
		return view;
	}
	
	@RequestMapping("/queryPark")
	public ModelAndView queryPark(@CookieValue(value = "firstCarNo", defaultValue = "") String firstCarNo,
			@CookieValue(value = "secondCarNo", defaultValue = "") String secondCarNo){
		
		String name = "/leave/queryPark";

		ModelAndView view = ViewUtil.view(name);

		view.addObject("firstCarNo", Base64Util.decodeString(firstCarNo));
		view.addObject("secondCarNo", Base64Util.decodeString(secondCarNo));

		return view;
	}
	
	@RequestMapping("/checkCarNo")
	@ResponseBody
	public Map<String,Object> checkCarNo(@CookieValue(value = "firstCarNo", defaultValue = "") String firstCarNo,
			@CookieValue(value = "secondCarNo", defaultValue = "") String secondCarNo,String carNo,HttpServletResponse response){
		
		if(StringUtils.isEmpty(carNo)){
			
			
		}else{
			
			String encodeCarNo = Base64Util.encodeString(carNo);
			
			if(!firstCarNo.equals(encodeCarNo) && !secondCarNo.equals(encodeCarNo)){
				Cookie first = new Cookie("firstCarNo",encodeCarNo);
				Cookie second = new Cookie("secondCarNo", firstCarNo);

				response.addCookie(first);
				response.addCookie(second);
			}
		}
		
		
		return Ajax.getSuccessReturnMapWithData("03-0001");
	}
	
	
	/**
	 * 导览到停车场出入口
	 * @param request
	 * @param parkLocation
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toParkEntrance")
	public ModelAndView toParkEntrance(HttpServletRequest request,String parkLocation) throws Exception {
		String name = "/leave/toPark";
		ModelAndView view = ViewUtil.view(name);
		
		String userOpenId = SessionHelper.getLoginUserOpenId(request.getSession());
		UserLocation ul = locationService.findUserLocation(userOpenId);
		
		if(ul!=null){
			
			if(StringUtils.isNotEmpty(parkLocation) && parkLocation.length() == 7){
				parkLocation = parkLocation + "-0001";
			}
			
			//选择性路线 选取唯一路线
			FromToOptimize fromTo = locationService.getFromTo(ul.getLeaveLocation().substring(0, 7), parkLocation.substring(0, 7));
			
			LoToLoBO bo = locationService.loadLoToLoBO(fromTo.getFromCode(),parkLocation);
			view.addObject("guide", GuideVO.boToVo(bo,Lo2LoStepType.IN,true));
			
		}else{
			//未记录位置信息 如何处理  返回首页
		}
		
		return view;
	}
	
	
	@RequestMapping("/toLocation")
	public ModelAndView toLocation(HttpServletRequest request,String parkLocation) throws Exception {
		String name = "/leave/toLocation";
		ModelAndView view = ViewUtil.view(name);
		
		String userOpenId = SessionHelper.getLoginUserOpenId(request.getSession());
		UserLocation ul = locationService.findUserLocation(userOpenId);
		
		if(ul!=null){
			
			if(StringUtils.isNotEmpty(ul.getParkLocation()) && ul.getParkLocation().length()==12){
				parkLocation = ul.getParkLocation();
			}
			
			//选择性路线 选取唯一路线
			FromToOptimize fromTo = locationService.getFromTo(ul.getLeaveLocation().substring(0, 7), parkLocation.substring(0, 7));
			
			LoToLoBO bo = locationService.loadLoToLoBO(fromTo.getFromCode(),parkLocation);
			view.addObject("guide", GuideVO.boToVo(bo,Lo2LoStepType.IN));
			
		}else{
			//未记录位置信息 如何处理  返回首页
		}
		
		return view;
	}
}
