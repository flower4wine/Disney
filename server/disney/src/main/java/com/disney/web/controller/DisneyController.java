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

import com.disney.handler.config.SessionHelper;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.QrCode;
import com.disney.model.UserLocation;
import com.disney.service.LocationService;
import com.disney.util.Ajax;
import com.disney.util.DateUtils;
import com.disney.util.ViewUtil;
import com.disney.util.WeChatBaseUtil;

@Controller
public class DisneyController {

	@Autowired
	private WeChatHandler weChatHandler;

	@Autowired
	private LocationService locationService;


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
			view.addObject("parkCode", "");
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

	@RequestMapping("/parkpay/queryParkPay")
	public String queryParkPay() {

		String name = "/parkpay/queryParkPay";

		return name;
	}


	@RequestMapping("/parkpay/parkpay")
	public String parkpay() {

		String name = "/parkpay/parkPay";

		return name;
	}

}