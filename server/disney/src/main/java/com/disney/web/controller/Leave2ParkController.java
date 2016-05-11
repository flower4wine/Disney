package com.disney.web.controller;

import java.util.Date;
import java.util.List;
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
import com.disney.exception.JSApiException;
import com.disney.handler.config.SessionHelper;
import com.disney.handler.jieshun.JieShunService;
import com.disney.handler.message.MessageHandler;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.Location;
import com.disney.model.UserLocation;
import com.disney.service.Lo2loService;
import com.disney.service.LocationService;
import com.disney.util.Ajax;
import com.disney.util.Base64Util;
import com.disney.util.DateUtils;
import com.disney.util.MapToVOUtil;
import com.disney.util.ViewUtil;
import com.disney.web.vo.GuideVO;
import com.disney.web.vo.LocationVO;
import com.disney.web.vo.jieshunapivo.QueryCarByCarnoVO;

@Controller
@RequestMapping("/le")
public class Leave2ParkController {

	@Autowired
	private WeChatHandler weChatHandler;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private MessageHandler messageHandler;
	
	@Autowired
	private Lo2loService lo2loService;
	
	@Autowired
	private JieShunService jieShunService;

	
	@RequestMapping("/lo")
	public ModelAndView parkLocation(HttpServletRequest request,String co) throws Exception {
		//Validate check error
		if(StringUtils.isEmpty(co) || co.length()!=12){
			return ViewUtil.error("10001");
		}
		
		Location parent = locationService.find(co.substring(0,7));
		Location child = locationService.find(co);
		
		String name = "/leave/location";
		ModelAndView view = ViewUtil.view(name);
		LocationVO vo = new LocationVO();
		
		if(parent==null || child==null){
			//位置信息不存在
			return ViewUtil.error("10005");
		}
		
		vo.setCode(co);
		vo.setRemark(parent.getName());
		vo.setLocationImg(parent.getLocationImg());

		//Get WeXin info
		ViewUtil.getWeChatInfo(view, weChatHandler, request);
				
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
			@CookieValue(value = "secondCarNo", defaultValue = "") 
			String secondCarNo,String carNo,HttpServletResponse response) throws JSApiException{
		String parkPlaceCode =  null;
		if(StringUtils.isEmpty(carNo)){
			
			return Ajax.buildErrorResult(messageHandler.getErrorMessage("10006"));
			
		}else{
			
			String encodeCarNo = Base64Util.encodeString(carNo);
			
			if(!firstCarNo.equals(encodeCarNo) && !secondCarNo.equals(encodeCarNo)){
				Cookie first = new Cookie("firstCarNo",encodeCarNo);
				Cookie second = new Cookie("secondCarNo", firstCarNo);

				response.addCookie(first);
				response.addCookie(second);
			}
			
			Map<String, Object> query = jieShunService.queryCarStopByCarno(carNo);
			
			
			List<QueryCarByCarnoVO> queryCarStopByCarno = MapToVOUtil.mapToQueryCarByCarnoVO(query);
			if(queryCarStopByCarno.isEmpty() || queryCarStopByCarno.size() <= 0){
				return Ajax.buildErrorResult("查找不到对应的车辆位置。");
			}
			for (QueryCarByCarnoVO vo : queryCarStopByCarno) {
				parkPlaceCode = vo.getParkingCode();
			}
		}
		
		return Ajax.getSuccessReturnMapWithData(parkPlaceCode);
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
			
			//不支持同一个停车场内部的导览
			if(ul.getLeaveLocation().substring(0,7).equals(parkLocation.substring(0,7))){
				//未记录位置信息 如何处理  返回首页
				return ViewUtil.error("10008");
			}
			
			LoToLoBO bo = lo2loService.loadLoToLoBO(ul.getLeaveLocation(),parkLocation);
			
			if(bo==null){
				return ViewUtil.error("10004");
			}
			
			view.addObject("guide", GuideVO.boToVo(bo,Lo2LoStepType.IN,true));
			
		}else{
			//未记录位置信息 如何处理  返回首页
			return ViewUtil.error("10007");
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
			
			//不支持同一个停车场内部的导览
			if(ul.getLeaveLocation().substring(0,7).equals(parkLocation.substring(0,7))){
				//未记录位置信息 如何处理  返回首页
				return ViewUtil.error("10008");
			}
			
			LoToLoBO bo = lo2loService.loadLoToLoBO(ul.getLeaveLocation(),parkLocation);
			
			if(bo==null){
				return ViewUtil.error("10004");
			}
			
			if(bo!=null){
				view.addObject("guide", GuideVO.boToVo(bo,Lo2LoStepType.IN));
			}
			
		}else{
			//未记录位置信息 如何处理  返回首页
			return ViewUtil.error("10007");
		}
		
		return view;
	}
}
