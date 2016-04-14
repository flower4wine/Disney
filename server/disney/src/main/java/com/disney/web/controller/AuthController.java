package com.disney.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.disney.handler.config.SessionHelper;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.util.ViewUtil;

@Controller
public class AuthController {
	
	@Autowired
	WeChatHandler wxHandler;

	@RequestMapping("/auth")
	public ModelAndView auth() {
		ModelAndView view = ViewUtil.view("/auth");
		
		if(wxHandler.isDebug()){
			view.setViewName("redirect:/wxlogin.html");
		}else{
			view.addObject("authUrl",wxHandler.getWXBaseAuthUrl());
		}
		
	 	
		return view;
	}

	@RequestMapping("/wxlogin")
	public ModelAndView wxlogin(String code,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String toUrl = SessionHelper.getToUrl(request);
		
		//Judge if from wexin forward
		if(StringUtils.isNotEmpty(code)){
			Map<String,Object> token = wxHandler.getAuthInfo(code);
			
			if(token == null || token.get("openid")==null){
				//获得Token失败
				
			}else{
				SessionHelper.setUserOpenId(session, token.get("openid").toString());
				//Clear to URL, then redirect to last intercept URL
				SessionHelper.clearToUrl(session);
			}
			
		}else{
			boolean isDebug = wxHandler.isDebug();
			if(isDebug){
				if(!SessionHelper.isLogin(session)){
					SessionHelper.setUserOpenId(session, "test");
				}
			}
		}
		
		return ViewUtil.view("redirect:"+toUrl);
	}
	
	
}
