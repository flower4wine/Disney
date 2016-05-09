package com.disney.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disney.handler.jieshun.JieShunService;
import com.disney.util.Ajax;

@RequestMapping("/jieshun")
@Controller
public class JieShunApiController {

	@Autowired
	private JieShunService jieShunService;

	@RequestMapping("/jslogin")
	@ResponseBody
	public Map<String, Object> jstest() throws Exception {

		// 客户号
		String cid = "880002101002155";
		// 帐号
		String user = "880002101002155";
		// 密码
		String password = "123456";

		String version = "2";

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


}
