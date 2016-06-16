package com.disney.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/jieshun")
@Controller
public class JieShunApiController {/*

	@Autowired
	private JieShunService jieShunService;
	
	@Autowired
	private JieShunConfigHandler jieShunConfigHandler;

	@RequestMapping("/jslogin")
	@ResponseBody
	public Map<String, Object> jstest() throws Exception {

		// 客户号
		String cid = jieShunConfigHandler.getConfigValue(JSConfigKey.CID);//"880002101002155";
		// 帐号
		String user = jieShunConfigHandler.getConfigValue(JSConfigKey.USER);//"880002101002155";
		// 密码
		String password = jieShunConfigHandler.getConfigValue(JSConfigKey.PWD);//"123456";

		String version = jieShunConfigHandler.getConfigValue(JSConfigKey.VERSION);//"2";

		String token = jieShunService.getLoginToken(cid, user, password, version);

		return Ajax.getSuccessReturnMapWithData(token);
	}

	@RequestMapping("/queryParkSpace")
	@ResponseBody
	public Map<String, Object> queryParkSpace() throws Exception {
		Map<String, Object> queryParkSpace = jieShunService.queryParkSpace();
		return Ajax.getSuccessReturnMapWithData(queryParkSpace);
	}


	@RequestMapping("/queryCarByCarno")
	@ResponseBody 
	public Map<String,Object> queryCarByCarno() throws Exception{
		String carNo = "沪-M69129蓝";
		return Ajax.getSuccessReturnMapWithData(jieShunService.queryCarStopByCarno(carNo)); 
	}

	@RequestMapping("/queryCarInfoByCarno")
	@ResponseBody 
	public Map<String,Object> queryCarInfoByCarno() throws Exception{
		String carNo = "沪-M69129蓝";
		return Ajax.getSuccessReturnMapWithData(jieShunService.queryCarInfoByCarno(carNo)); 
	}

	@RequestMapping("/queryResultsByCarno")
	@ResponseBody 
	public Map<String,Object> queryResultsByCarno() throws Exception{
		String carNo = "沪-M69129蓝";
		return Ajax.getSuccessReturnMapWithData(jieShunService.payByCarno(carNo)); 
	}


*/}
