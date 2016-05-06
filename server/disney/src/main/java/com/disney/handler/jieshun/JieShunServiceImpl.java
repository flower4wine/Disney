package com.disney.handler.jieshun;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.disney.exception.JSApiException;
import com.disney.handler.jieshun.api.ApiHandler;
import com.disney.handler.jieshun.api.JSApiRequestApiBO;
import com.disney.handler.jieshun.api.JSApiResultBO;
import com.disney.handler.jieshun.api.JSLoginBO;
import com.disney.handler.jieshun.constant.LoginUser;

@Service
public class JieShunServiceImpl implements JieShunService{

	@Override
	public String getLoginToken(String cid, String user, String password,String version) throws JSApiException {

		String url = "http://preapi.jslife.net/jsaims/login";	
		return  JieShunHandler.getLoginToken(cid, user, password,version, url);

	}


	@Override
	public Map<String,Object> queryParkSpace() throws JSApiException {
		JSApiRequestApiBO apiBO = new JSApiRequestApiBO();

		apiBO.setServiceId("3c.park.queryparkspace");
		apiBO.setRequestType("DATA");

		Map<String,String> param = new HashMap<String,String>();

		param.put("parkCodes", "0000002236");

		apiBO.setAttrs(param);

		JSLoginBO loginBo = new JSLoginBO();

		loginBo.setCid(LoginUser.cid);
		loginBo.setVersion(LoginUser.version);
		loginBo.setLoginToken(getLoginToken(LoginUser.cid,LoginUser.user,LoginUser.password,LoginUser.version));

		JSApiResultBO result = ApiHandler.execute(apiBO, loginBo);

		Map<String,Object> json =  result.getReturnObject();
		
		return json;

	}

	@Override
	public Map<String,Object> queryCarStopByCarno(String carNo) throws JSApiException {
		JSApiRequestApiBO apiBO = new JSApiRequestApiBO();

		apiBO.setServiceId("3c.park.querycarparkingspot");
		apiBO.setRequestType("DATA");

		Map<String,String> param = new HashMap<String,String>();

		param.put("parkCode", "0000002236");
		param.put("carNo", carNo);

		apiBO.setAttrs(param);

		JSLoginBO loginBo = new JSLoginBO();

		loginBo.setCid(LoginUser.cid);
		loginBo.setVersion(LoginUser.version);
		loginBo.setLoginToken(getLoginToken(LoginUser.cid,LoginUser.user,LoginUser.password,LoginUser.version));

		JSApiResultBO result = ApiHandler.execute(apiBO, loginBo);
		Map<String,Object> json =  result.getReturnObject();
		return json;
	}

	@Override
	public Map<String,Object> queryCarInfoByCarno(String carNo) throws JSApiException {
		
		Map<String, Object> querryOrderByCarNo = this.queryOrderByCarNo(carNo);
		
		return querryOrderByCarNo;
		
	}

	@Override
	public Map<String, Object> queryOrderByCarNo(String carNo) throws JSApiException {
		Map<String, Object> queryOrder = new HashMap<String,Object>();
		Map<String, Object> queryCarStopByCarno = this.queryCarStopByCarno(carNo);
		if(!queryCarStopByCarno.isEmpty() && queryCarStopByCarno.size()>0){
			String orderNo = this.createOrderByCarno(carNo);
			queryOrder =  this.queryOrder(orderNo);
		}
		return queryOrder;
	}

	@Override
	public Map<String,Object> payByCarno(String carNo) throws JSApiException {
		
		Map<String, Object> queryCarStopByCarno = this.queryCarStopByCarno(carNo);
		if(!queryCarStopByCarno.isEmpty() && queryCarStopByCarno.size()>0){
			String orderNo = this.createOrderByCarno(carNo);
			Map<String, Object> queryOrder = this.queryOrder(orderNo);
			Double serviceFee = (Double) queryOrder.get("serviceFee");
			//添加微信支付
		}
		
		return queryCarStopByCarno;
	}

	@Override
	public String createOrderByCarno(String carNo) throws JSApiException {
		String r = null;
		JSApiRequestApiBO apiBO = new JSApiRequestApiBO();

		apiBO.setServiceId("3c.pay.createorderbycarno");
		apiBO.setRequestType("DATA");

		Map<String,String> param = new HashMap<String,String>();

		param.put("parkCode", "0000002236");
		param.put("businesserCode", "880002101002155");
		param.put("orderType", "VNP");
		param.put("carNo", carNo);

		apiBO.setAttrs(param);

		JSLoginBO loginBo = new JSLoginBO();

		loginBo.setCid(LoginUser.cid);
		loginBo.setVersion(LoginUser.version);
		loginBo.setLoginToken(getLoginToken(LoginUser.cid,LoginUser.user,LoginUser.password,LoginUser.version));

		JSApiResultBO result = ApiHandler.execute(apiBO, loginBo);
		Map<String,Object> json =  result.getReturnObject();
		if(!json.isEmpty() && json.size()>0){
			r = "创建订单失败";
		}else{
			r = (String) json.get("orderNo");
		}
		return r;
	}

	@Override
	public Map<String, Object> queryOrder(String orderNo) throws JSApiException {
		JSApiRequestApiBO apiBO = new JSApiRequestApiBO();

		apiBO.setServiceId("3c.pay.queryorder");
		apiBO.setRequestType("DATA");

		Map<String,String> param = new HashMap<String,String>();

		param.put("orderNo", orderNo);

		apiBO.setAttrs(param);

		JSLoginBO loginBo = new JSLoginBO();

		loginBo.setCid(LoginUser.cid);
		loginBo.setVersion(LoginUser.version);
		loginBo.setLoginToken(getLoginToken(LoginUser.cid,LoginUser.user,LoginUser.password,LoginUser.version));

		JSApiResultBO result = ApiHandler.execute(apiBO, loginBo);
		Map<String,Object> json =  result.getReturnObject();
		return json;
	}

}
