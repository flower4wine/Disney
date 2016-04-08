package com.disney.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DisneyController {


	@RequestMapping("/index")
	public String index() {

		String name = "/index";

		return name;
	}

	
	@RequestMapping("/leave/queryPark")
	public String queryPark() {

		String name = "/leave/queryPark";

		return name;
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

/*	@Autowired
	private LocationService locationService;
	
	@RequestMapping("/locations")
	@ResponseBody
	public List<Location> locations() {
		return locationService.findAll();
	}*/
	
}
