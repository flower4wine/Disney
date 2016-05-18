package com.disney.handler.message;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionHandler implements HandlerExceptionResolver {
	
	private static Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		Map<String, Object> model = new HashMap<String, Object>(); 
		
		model.put("errorMessage", ex.getMessage());  

		ex.printStackTrace();
		log.error(ex.getMessage());
		
		return new ModelAndView("/error", model);  
	}

}
