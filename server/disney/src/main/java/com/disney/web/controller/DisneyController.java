package com.disney.web.controller;

import java.util.Date;
import java.util.List;
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
import com.disney.handler.jieshun.JieShunService;
import com.disney.handler.message.MessageHandler;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.QrCode;
import com.disney.model.UserLocation;
import com.disney.service.LocationService;
import com.disney.util.Ajax;
import com.disney.util.DateUtils;
import com.disney.util.MapToVOUtil;
import com.disney.util.ViewUtil;
import com.disney.util.WeChatBaseUtil;
import com.disney.web.vo.jieshunapivo.QueryOrderVO;

@Controller
public class DisneyController {

	@Autowired
	private WeChatHandler weChatHandler;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private MessageHandler messageHandler;
	
	@Autowired
	private JieShunService jieShunService;


	@RequestMapping("/disney")
	public String index() {
		return "redirect:index.html";
	}

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) throws Exception {

		String name = "/index";
		ModelAndView view = ViewUtil.view(name);

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

		String userOpenId = SessionHelper.getLoginUserOpenId(request.getSession());
		UserLocation ul = locationService.findUserLocation(userOpenId);
		Date now = DateUtils.getStartDate(new Date());

		if(ul!=null && DateUtils.getStartDate(ul.getCreatedAt()).compareTo(now) == 0 ){
			view.addObject("parkCode", ul.getParkLocation());
			view.addObject("leaveCode", ul.getLeaveLocation());

		}else{
			
			if(weChatHandler.isDebug()){
				view.addObject("parkCode", "03-0001-0001");
				view.addObject("leaveCode", "05-0001-0001");
			}else{
				view.addObject("parkCode", "");
				view.addObject("leaveCode", "");
			}
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
		
		Map<String, Object> query = jieShunService.queryOrderByCarNo(carNo);
		
		List<QueryOrderVO> queryOrderByCarNo = MapToVOUtil.mapToQueryOrderVO(query);
		
		view.addObject("queryOrderByCarNo", queryOrderByCarNo);
		
		return view;
	}

}
