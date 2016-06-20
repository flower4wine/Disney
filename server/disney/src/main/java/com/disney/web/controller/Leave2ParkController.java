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
import com.disney.bo.QrCodeBO;
import com.disney.constant.Lo2LoStepType;
import com.disney.exception.JSApiException;
import com.disney.handler.config.SessionHelper;
import com.disney.handler.message.MessageHandler;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.Location;
import com.disney.model.Park;
import com.disney.model.UserLocation;
import com.disney.service.Lo2loService;
import com.disney.service.LocationService;
import com.disney.service.ParkService;
import com.disney.util.Ajax;
import com.disney.util.Base64Util;
import com.disney.util.DateUtils;
import com.disney.util.ViewUtil;
import com.disney.web.vo.GuideVO;
import com.disney.web.vo.LocationVO;

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
	private ParkService parkService;

	
	@RequestMapping("/lo")
	public ModelAndView parkLocation(HttpServletRequest request,String co) throws Exception {
		//Validate check error
		if(StringUtils.isEmpty(co) || co.length()!=12){
			return ViewUtil.error("10001");
		}
		
		String name = "/leave/location";
		ModelAndView view = ViewUtil.view(name);
		LocationVO vo = new LocationVO();
		
		QrCodeBO bo = locationService.queryQrCodeInfo(co);
		Location parentLoc = locationService.find(co.substring(0,7));
		
		if(bo==null || parentLoc==null){
			//位置信息不存在
			return ViewUtil.error("10005");
		}
		
		vo.setCode(bo.getQrode());
		vo.setLocationImg(bo.getLocationImg());
		vo.setName(parentLoc.getName());
		
		if(co.startsWith("03")){
			
			String region = StringUtils.isEmpty(bo.getRegion()) ? "" : bo.getRegion()+"  ";
			String range = StringUtils.isEmpty(bo.getCodeRange()) ? "" : (bo.getCodeRange()+"  附近");
			vo.setRemark(region + range);
			vo.setParkLocation(true);
			
		}else if(co.startsWith("02")){
			vo.setParkLocation(false);
			vo.setRemark(bo.getQrLocationName());
		}else if(co.startsWith("05")){
			vo.setParkLocation(false);
			vo.setLocationImg(parentLoc.getLocationImg());
			vo.setRemark(parentLoc.getName());
			vo.setName("位置信息");
		}
		
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
			ul.setScanLocation(code);
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
			
			if(weChatHandler.isDebug()){
				parkPlaceCode = "03-0001";
			}else{
				Park queryCarInPark = parkService.queryCarInPark(carNo);
				parkPlaceCode = queryCarInPark.getQrCode();
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
				// 根据 parkLocation判断停车位置是否靠近出入口  如果靠近出入口需要 忽略内部导览。 通过LoToLoBO 返回判断
				if(bo.isIgnoreInner()){
					view.addObject("guide", GuideVO.boToVo(bo,Lo2LoStepType.IN,true));
				}else{
					view.addObject("guide", GuideVO.boToVo(bo,Lo2LoStepType.IN));
				}
			}
			
		}else{
			//未记录位置信息 如何处理  返回首页
			return ViewUtil.error("10007");
		}
		
		return view;
	}
}
