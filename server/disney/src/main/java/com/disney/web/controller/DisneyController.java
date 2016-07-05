package com.disney.web.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.disney.exception.JSApiException;
import com.disney.handler.config.SessionHelper;
import com.disney.handler.message.MessageHandler;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.Location;
import com.disney.model.QrCode;
import com.disney.model.UserLocation;
import com.disney.service.LocationService;
import com.disney.service.ParkService;
import com.disney.util.Ajax;
import com.disney.util.DateUtils;
import com.disney.util.MapToVOUtil;
import com.disney.util.ViewUtil;
import com.disney.util.WeChatBaseUtil;
import com.disney.web.vo.QrCodeVerifyVO;
import com.disney.web.vo.jieshunvo.QueryOrderVO;

@Controller
public class DisneyController {

	@Autowired
	private WeChatHandler weChatHandler;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private MessageHandler messageHandler;
	
	@Autowired
	private ParkService parkService;
	
	@RequestMapping("/disney")
	public String index() {
		return "redirect:index.html";
	}
	
	@RequestMapping("/scanTool")
	public ModelAndView scanTool(HttpServletRequest request) throws Exception {
		
		ModelAndView view = ViewUtil.view("/scan");
		
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
		
		return view;
	}
	
	@RequestMapping("/scanVerify")
	@ResponseBody
	public Map<String,Object> scanVerify(String url) {
		
		QrCode code = locationService.findQrCode(url);
		String returnCode = "";

		if(code != null){
			returnCode = code.getQrCode();
		}
		
		//根据二维码号牌 解析内容
		if(StringUtils.isNotEmpty(returnCode)){
			//二维码核验VO
			QrCode map = locationService.findQrCodeByCode(returnCode);
			Location loc = locationService.find(returnCode);
			
			QrCodeVerifyVO vo = QrCodeVerifyVO.getVO(map,loc);
			
			if(vo!=null){
				return Ajax.getSuccessReturnMapWithData(vo);
			}
			
		}

		return Ajax.buildErrorResult("未找到二维码信息。");
		
	}
	

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) throws Exception {

		String name = "/index";
		ModelAndView view = ViewUtil.view(name);
		
		
		if(weChatHandler.isDebug()){
			view.addObject("guideCode", "03-0001-0001");
			view.addObject("leaveCode", "05-0001-0001");
			
			view.addObject("appId", "");
			view.addObject("timestamp", "");
			view.addObject("nonceStr", "");
			view.addObject("signature", "");
			
		}else{
			
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

			//登录信息
			String userOpenId = SessionHelper.getLoginUserOpenId(request.getSession());
			UserLocation ul = locationService.findUserLocation(userOpenId);
			Date now = DateUtils.getStartDate(new Date());
			
			if(ul!=null &&  ul.getCreatedAt()!=null  && DateUtils.getStartDate(ul.getCreatedAt()).compareTo(now) == 0 ){
				
				if(StringUtils.isNotEmpty(ul.getScanLocation())){
					//停车导览 只取停车场内部
					if(ul.getScanLocation().startsWith("03")||ul.getScanLocation().startsWith("02-0001")){
						view.addObject("guideCode", ul.getScanLocation());
					}else{
						view.addObject("guideCode", ul.getParkLocation());
					}
					
				}else{
					view.addObject("guideCode", ul.getParkLocation());
				}
				
				
			}else{
				view.addObject("guideCode", "");
			}
			
			
			view.addObject("leaveCode", "");
			
		}

		return view;
	}

	@RequestMapping("/getScanCode")
	@ResponseBody
	public Map<String,Object> getScanCode(String scanResult){
		QrCode code = locationService.findQrCode(scanResult);
		String returnCode = "";

		if(code != null){
			returnCode = code.getQrCode();
		}

		return Ajax.getSuccessReturnMapWithData(returnCode);
	}
	
	@RequestMapping("/error")
	public ModelAndView error(String errorCode,String errorMessage){
		
		ModelAndView view = ViewUtil.view("/error");
		
		if(StringUtils.isNotEmpty(errorMessage)){
			view.addObject("errorMessage", errorMessage);
		}else{
			view.addObject("errorMessage", messageHandler.getErrorMessage(errorCode));
		}
		
		return view;
	}
	
	
	@RequestMapping("/errorAsyn")
	@ResponseBody
	public String errorAsyn(String errorCode){
		return messageHandler.getErrorMessage(errorCode);
	}
	
	
	@RequestMapping("/parkpay/queryParkPay")
	public String queryParkPay() {

		String name = "/parkpay/queryParkPay";

		return name;
	}


	@RequestMapping("/parkpay/parkpay")
	public ModelAndView parkpay(String carNo) throws JSApiException {

		String name = "/parkpay/parkPay";
		ModelAndView view = ViewUtil.view(name);
		
		if(weChatHandler.isDebug()){
			view.addObject("order", new QueryOrderVO());
		}else{
			Map<String, Object> query = parkService.queryOrder(carNo);
			QueryOrderVO orderVO = MapToVOUtil.mapToQueryOrderVO(query);
			view.addObject("order", orderVO);
		}
		
		return view;
	}
	
	@RequestMapping("/introduction")
	public ModelAndView introduction() throws JSApiException {

		String name = "/introduction";
		
		ModelAndView view = ViewUtil.view(name);
		
		return view;
	}

}
