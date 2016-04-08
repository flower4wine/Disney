package com.disney.util;

import org.springframework.web.servlet.ModelAndView;

public class ViewUtil {
	
	public static  ModelAndView view(String name){
		ModelAndView view = new ModelAndView();
		view.setViewName(name);
		return view;
	}
}
