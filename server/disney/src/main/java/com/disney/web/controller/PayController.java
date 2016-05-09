package com.disney.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disney.bo.pingplus.PingOneBO;
import com.disney.constant.PingPlusChannel;
import com.disney.handler.config.SessionHelper;
import com.disney.handler.pingplus.PingPlusService;
import com.disney.util.JsonUtil;
import com.disney.util.NumberUtil;
import com.pingplusplus.model.Charge;

@Controller
@RequestMapping("/pay")
public class PayController {


	@Autowired
	private PingPlusService pingPlusService;
	
	//private static Logger log = LoggerFactory.getLogger(PayController.class);

	/**
	 * 支付成功以后操作
	 * @return
	 */
	@RequestMapping(value="/paySuccess.html",method = RequestMethod.GET)
	public String paySuccess(){
		return "/pay/success";
	}

	/**
	 * 调用Ping++支付后的成功回调接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/paySuccessCharge.html")
	@ResponseBody
	public Charge charge(HttpServletRequest request){
		Charge charge = null;

		String requestJson = getRequestPayload(request);
		Map<String, Object>  params =  null;


		try {
			params = JsonUtil.readJson2Map(requestJson);
			params.put("requestIp",  request.getRemoteAddr());

			String openId = SessionHelper.getLoginUserOpenId(request.getSession());
			System.out.println(openId);

			//here to get charge
			charge = getCharge(params,"");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return charge;
	}

	private String getRequestPayload(HttpServletRequest req) {  

		StringBuilder sb = new StringBuilder();  

		try {
			BufferedReader reader = req.getReader();
			char[]buff = new char[1024];  
			int len;  
			while((len = reader.read(buff)) != -1) {  
				sb.append(buff,0, len);  
			}  
		} catch (IOException e) {
		}

		return sb.toString();  
	}  

	private  Charge getCharge(Map<String, Object>  params,String carNo) throws Exception {

		Charge charge = null;


		Map<String, Object> chargeMap = new HashMap<String, Object>();
		chargeMap.put("currency", "cny");

		String msg = "";

		msg = "车牌号：" + carNo+" 进行临停缴费。";

		chargeMap.put("subject", msg);
		chargeMap.put("body", msg);


		chargeMap.put("amount", NumberUtil.toInteger(params.get("amount")));

		chargeMap.put("order_no", params.get("order_no"));
		chargeMap.put("channel", params.get("channel"));
		chargeMap.put("client_ip", params.get("requestIp"));

		Map<String, String> app = new HashMap<String, String>();

		app.put("id","");
		chargeMap.put("app", app);

		//Add extra parameter
		chargeMap.put("extra",handleExtraWithChannel(params));

		//发起交易请求
		charge = Charge.create(chargeMap);

		if(charge!=null){

		}

		return charge;
	}

	private Map<String, Object> handleExtraWithChannel(Map<String, Object>  params ){
		Map<String, Object> extra = new HashMap<String, Object>();

		String channel =  (String)params.get(PingPlusChannel.CHANNEL);

		if(channel.equals(PingPlusChannel.WXPUB)){
			extra.put("open_id", params.get("open_id"));
		}

		PingOneBO oneBO = pingPlusService.getOneBO();

		if(channel.equals(PingPlusChannel.ALIPAYWAP)){
			extra.put("success_url", oneBO.getAliPaySuccessUrl());
			extra.put("cancel_url", oneBO.getAliPaySuccessUrl());
		}
		return extra;
	}
	
	
	
	
	@RequestMapping(value="/alipay/pay.html",method = RequestMethod.GET)
	public String pay(){
		return "/pay/alipay";
	}


	@RequestMapping(value="/alipaySuccess.html",method = RequestMethod.GET)
	public String aliPaySuccess(){
		return "/pay/success";
	}
	
	
	@RequestMapping(value="/pingpp/webhooks/test.html",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> testChargeSuccess(HttpServletRequest request) throws Exception{
		return chargeSuccess(request);
	}

	@RequestMapping(value="/pingpp/webhooks/success.html",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> chargeSuccess(HttpServletRequest request) throws Exception{
		return null;
	}


}
