package com.disney.handler.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;



public class SessionHelper {

	public static String OPEN_USER = "openUser";
	public static String TO_URL = "toUrl";
	
	public static String DEFAULT_TO_URL = "/";

	public static boolean isLogin(HttpSession session){
		Object o = session.getAttribute(OPEN_USER);

		if(o != null){
			return true;
		}

		return false;

	}

	public static String getLoginUserOpenId(HttpSession session){
		Object o = session.getAttribute(OPEN_USER);

		if(o != null){
			return o.toString();
		}

		return null;
	}

	public static void setUserOpenId(HttpSession session,String openId){
		session.setAttribute(OPEN_USER, openId);
	}
	
	public static void remeberToUrl(HttpServletRequest request){
		String toUrl = request.getServletPath();   
		String queryString = request.getQueryString();
		
		if(StringUtils.isNotEmpty(queryString)){
			toUrl += "?"+ queryString;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute(TO_URL, toUrl);
	}
	
	
	public static void clearToUrl(HttpSession session){
		session.removeAttribute(TO_URL);
	}
	
	
	public static String getToUrl(HttpServletRequest request){
		HttpSession session = request.getSession();
		
		Object o = session.getAttribute(TO_URL);
		
		if(o==null){
			return  DEFAULT_TO_URL;
		}
		
		return o.toString();
	}

}
