package com.disney.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disney.util.Ajax;

@Controller
public class SystemRuntimeController {
	
	@RequestMapping("/runtime")
	@ResponseBody
	public Map<String,Object> runtime() {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		double total = (Runtime.getRuntime().totalMemory()) / (1024.0 * 1024);
		double max = (Runtime.getRuntime().maxMemory()) / (1024.0 * 1024);
		double free = (Runtime.getRuntime().freeMemory()) / (1024.0 * 1024);
				
		map.put("Max:", "Java 虚拟机试图使用的最大内存量(当前JVM的最大可用内存)maxMemory(): " + max + "MB");
		map.put("Total:", "Java 虚拟机中的内存总量(当前JVM占用的内存总数)totalMemory(): " + total + "MB");
		map.put("Free:", "Java 虚拟机中的空闲内存量(当前JVM空闲内存)freeMemory(): " + free + "MB");
		
		map.put("Message", "JVM实际可用内存: " + (max - total + free) + "MB");
		
		return Ajax.getSuccessReturnMapWithData(map);
	}

}
