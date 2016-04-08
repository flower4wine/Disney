package com.disney.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ParkController {

	@RequestMapping("/carport/parks")
	public String parks() {
		String name = "/park/parks";

		
		
		return name;
	}

	@RequestMapping("/carport/park")
	public String park() {
		String name = "/park/park";

		
		
		return name;
	}

}
