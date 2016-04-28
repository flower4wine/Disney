package com.disney.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disney.handler.jieshun.JieShunService;
import com.disney.util.Ajax;

@Controller
public class JieShunApiController {

	@Autowired
	private JieShunService jieShunService;

	@RequestMapping("/jstest")
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
		return Ajax.getSuccessReturnMapWithData(jieShunService.queryParkSpace());
	}


	@RequestMapping("/queryCarByCarno")
	@ResponseBody 
	public Map<String,Object> queryCarByCarno() throws Exception{

		/*String parkCodes="000000223"; String carNo="";

		Map<String, String> attrs = apiBO.getAttrs(); attrs.put("parkCodes",
				parkCodes); attrs.put("carNo",carNo);

				jieShunService.queryCarByCarno(apiBO);*/

		return Ajax.getSuccessReturnMapWithData(null); 
	}

	@RequestMapping("/queryCarInfoByCarno")
	@ResponseBody 
	public Map<String,Object>	queryCarInfoByCarno() throws Exception{

		/*String parkCodes="000000223"; String carNo="";

		Map<String, String> attrs = apiBO.getAttrs(); attrs.put("parkCodes",
				parkCodes); attrs.put("carNo",carNo);

				jieShunService.queryCarInfoByCarno(apiBO);*/

		return Ajax.getSuccessReturnMapWithData(null); 
	}

	@RequestMapping("/payByCarno")
	@ResponseBody 
	public Map<String,Object> payByCarno() throws Exception{

		/*String parkCodes="000000223"; String carNo=""; String orderNo = "";
		Double serviceFee = 0.0;

		Map<String, String> attrs = apiBO.getAttrs(); attrs.put("parkCodes",
				parkCodes); attrs.put("carNo",carNo); attrs.put("orderNo",orderNo);
				attrs.put("serviceFee",serviceFee.toString());

				jieShunService.payByCarno(apiBO);*/

		return Ajax.getSuccessReturnMapWithData(null); 
	}


}
