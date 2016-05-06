package com.disney.web.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.disney.exception.JSApiException;
import com.disney.handler.jieshun.JieShunService;
import com.disney.util.ViewUtil;

@Controller
public class ParkController {
	
	@Autowired
	private JieShunService jieShunService;

	@RequestMapping("/carport/parks")
	public ModelAndView parks() throws JSApiException {
		String name = "/park/parks";
		ModelAndView view = ViewUtil.view(name);
		Map<String, Object> queryParkSpace = jieShunService.queryParkSpace();
		view.addObject("parks", queryParkSpace);
		return view;
	}

	@RequestMapping("/carport/park")
	public ModelAndView park(String code) {
		String name = "/park/park";
		ModelAndView view = ViewUtil.view(name);

		Pattern pattern = Pattern.compile("03-000[1234]");
		Matcher matcher = pattern.matcher(code);
		
		if(matcher.matches()){
			view.addObject("bgImg", "/resources/img/location/"+code+".jpg");
		}
		
		int left = 0;
		int bottom = 0;
		
		if(code.equals("03-0001")){
			left = 15;
			bottom =30;
		}else if(code.equals("03-0002")){
			left = 40;
			bottom =31;
		}else if(code.equals("03-0003")){
			left = 55;
			bottom =50;
		}else if(code.equals("03-0004")){
			left = 55;
			bottom =58;
		}
		
		
		view.addObject("left", left);
		view.addObject("bottom", bottom);

		return view;
	}

}