package com.disney.handler.jieshun;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disney.exception.JSApiException;
import com.disney.handler.jieshun.api.ApiHandler;
import com.disney.handler.jieshun.api.JSApiRequestApiBO;
import com.disney.handler.jieshun.api.JSApiResultBO;
import com.disney.handler.jieshun.api.JSLoginBO;
import com.disney.handler.jieshun.constant.JSConfigKey;

@Service
public class JieShunServiceImpl implements JieShunService{
	
	@Autowired
	private JieShunConfigHandler jieShunConfigHandler;
	
	@Override
	public String getLoginToken(String cid, String user, String password,String version) throws JSApiException {

		String url = jieShunConfigHandler.getConfigValue(JSConfigKey.URL);
		return  JieShunHandler.getLoginToken(cid, user, password,version, url);

	}


	@Override
	public Map<String, Object> queryParkSpace() throws JSApiException {
		JSApiRequestApiBO apiBO = new JSApiRequestApiBO();

		apiBO.setServiceId(jieShunConfigHandler.getConfigValue(JSConfigKey.QUERYPARKSPACE));
		apiBO.setRequestType(jieShunConfigHandler.getConfigValue(JSConfigKey.REQUESTTYPE));

		Map<String,String> param = new HashMap<String,String>();

		param.put("parkCodes", jieShunConfigHandler.getConfigValue(JSConfigKey.PARKCODES));

		apiBO.setAttrs(param);

		JSLoginBO loginBo = new JSLoginBO();

		loginBo.setCid(jieShunConfigHandler.getConfigValue(JSConfigKey.CID));
		loginBo.setVersion(jieShunConfigHandler.getConfigValue(JSConfigKey.VERSION));
		loginBo.setLoginToken(getLoginToken(jieShunConfigHandler.getConfigValue(JSConfigKey.CID),
				jieShunConfigHandler.getConfigValue(JSConfigKey.USER),
				jieShunConfigHandler.getConfigValue(JSConfigKey.PWD),
				jieShunConfigHandler.getConfigValue(JSConfigKey.VERSION)));

		JSApiResultBO result = ApiHandler.execute(apiBO, loginBo);

		Map<String,Object> json =  result.getReturnObject();
		
		return json;

	}
	
	@Override
	public Map<String, Object> queryCarStopByCarno(String carNo) throws JSApiException {
		JSApiRequestApiBO apiBO = new JSApiRequestApiBO();

		apiBO.setServiceId(jieShunConfigHandler.getConfigValue(JSConfigKey.QUERYCARSTOPBYCARNO));
		apiBO.setRequestType(jieShunConfigHandler.getConfigValue(JSConfigKey.REQUESTTYPE));

		Map<String,String> param = new HashMap<String,String>();

		param.put("parkCode", jieShunConfigHandler.getConfigValue(JSConfigKey.PARKCODE));
		param.put("carNo", carNo);

		apiBO.setAttrs(param);

		JSLoginBO loginBo = new JSLoginBO();

		loginBo.setCid(jieShunConfigHandler.getConfigValue(JSConfigKey.CID));
		loginBo.setVersion(jieShunConfigHandler.getConfigValue(JSConfigKey.VERSION));
		loginBo.setLoginToken(getLoginToken(jieShunConfigHandler.getConfigValue(JSConfigKey.CID),
				jieShunConfigHandler.getConfigValue(JSConfigKey.USER),
				jieShunConfigHandler.getConfigValue(JSConfigKey.PWD),
				jieShunConfigHandler.getConfigValue(JSConfigKey.VERSION)));

		JSApiResultBO result = ApiHandler.execute(apiBO, loginBo);
		Map<String,Object> json =  result.getReturnObject();
		return json;
	}
	
	@Override
	public Map<String, Object> queryCarInfoByCarno(String carNo) throws JSApiException {
		
		Map<String, Object> queryOrderByCarNo = this.queryOrderByCarNo(carNo);

		return queryOrderByCarNo;
		
	}
	
	@Override
	public Map<String, Object> queryOrderByCarNo(String carNo) throws JSApiException {
		Map<String, Object>  queryOrder = new HashMap<String, Object>();
		Map<String, Object> queryCarStopByCarno = this.queryCarStopByCarno(carNo);
		if(!queryCarStopByCarno.isEmpty() && queryCarStopByCarno.size() > 0){
			String orderNo = this.createOrderByCarno(carNo);
			queryOrder = this.queryOrder(orderNo);
		}
		return queryOrder;
	}
	
	@Override
	public Map<String,Object> payByCarno(String carNo) throws JSApiException {
		
		Map<String, Object> queryCarStopByCarno = this.queryCarStopByCarno(carNo);
		if(!queryCarStopByCarno.isEmpty() && queryCarStopByCarno.size()>0){
			String orderNo = this.createOrderByCarno(carNo);
			Map<String, Object> queryOrder = this.queryOrder(orderNo);
			//添加微信支付
			System.out.println(queryOrder);
		}
		
		return null;
	}

	@Override
	public String createOrderByCarno(String carNo) throws JSApiException {
		String r = null;
		JSApiRequestApiBO apiBO = new JSApiRequestApiBO();

		apiBO.setServiceId(jieShunConfigHandler.getConfigValue(JSConfigKey.CREATEORDERBYCARNO));
		apiBO.setRequestType(jieShunConfigHandler.getConfigValue(JSConfigKey.REQUESTTYPE));

		Map<String,String> param = new HashMap<String,String>();

		param.put("parkCode", jieShunConfigHandler.getConfigValue(JSConfigKey.PARKCODE));
		param.put("businesserCode", jieShunConfigHandler.getConfigValue(JSConfigKey.BUSINESSERCODE));
		param.put("orderType", jieShunConfigHandler.getConfigValue(JSConfigKey.ORDERTYPE));
		param.put("carNo", carNo);

		apiBO.setAttrs(param);

		JSLoginBO loginBo = new JSLoginBO();

		loginBo.setCid(jieShunConfigHandler.getConfigValue(JSConfigKey.CID));
		loginBo.setVersion(jieShunConfigHandler.getConfigValue(JSConfigKey.VERSION));
		loginBo.setLoginToken(getLoginToken(jieShunConfigHandler.getConfigValue(JSConfigKey.CID),
				jieShunConfigHandler.getConfigValue(JSConfigKey.USER),
				jieShunConfigHandler.getConfigValue(JSConfigKey.PWD),
				jieShunConfigHandler.getConfigValue(JSConfigKey.VERSION)));

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
	public Map<String,Object> queryOrder(String orderNo) throws JSApiException {
		JSApiRequestApiBO apiBO = new JSApiRequestApiBO();

		apiBO.setServiceId(jieShunConfigHandler.getConfigValue(JSConfigKey.QUERYORDER));
		apiBO.setRequestType(jieShunConfigHandler.getConfigValue(JSConfigKey.REQUESTTYPE));

		Map<String,String> param = new HashMap<String,String>();

		param.put("orderNo", orderNo);

		apiBO.setAttrs(param);

		JSLoginBO loginBo = new JSLoginBO();

		loginBo.setCid(jieShunConfigHandler.getConfigValue(JSConfigKey.CID));
		loginBo.setVersion(jieShunConfigHandler.getConfigValue(JSConfigKey.VERSION));
		loginBo.setLoginToken(getLoginToken(jieShunConfigHandler.getConfigValue(JSConfigKey.CID),
				jieShunConfigHandler.getConfigValue(JSConfigKey.USER),
				jieShunConfigHandler.getConfigValue(JSConfigKey.PWD),
				jieShunConfigHandler.getConfigValue(JSConfigKey.VERSION)));

		JSApiResultBO result = ApiHandler.execute(apiBO, loginBo);
		Map<String,Object> json =  result.getReturnObject();
		return json;
	}

}
