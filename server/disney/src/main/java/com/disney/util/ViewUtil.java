package com.disney.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.disney.handler.wechat.WeChatHandler;

public class ViewUtil {

	public static  ModelAndView view(String name){
		ModelAndView view = new ModelAndView();
		view.setViewName(name);
		return view;
	}

	public static  ModelAndView error(String errorCode){
		ModelAndView view = new ModelAndView();

		view.setViewName("redirect:/error.html?errorCode="+errorCode);

		return view;
	}



	public static  void getWeChatInfo(ModelAndView view,WeChatHandler weChatHandler,HttpServletRequest request) throws Exception{

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
	}


}
