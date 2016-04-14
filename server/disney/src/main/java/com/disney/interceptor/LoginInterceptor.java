package com.disney.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.disney.handler.config.SessionHelper;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		
		if (!SessionHelper.isLogin(request.getSession())) {
			
			/*
			 * 这里记住被拦截的URL 登录成功以后自动跳转到上次拦截的URL去   
			 * 如注释不自动记住
			 * 未注释 则自动记住 跳转
			 */
			
			SessionHelper.remeberToUrl(request);
			response.sendRedirect(request.getContextPath()+"/auth.html");
			
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
	
}
