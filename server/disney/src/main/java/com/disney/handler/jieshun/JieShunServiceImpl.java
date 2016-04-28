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

		param.put("parkCodes", "000000223");

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
	public Map<String,Object> queryCarByCarno() throws JSApiException {
		JSApiRequestApiBO apiBO = new JSApiRequestApiBO();

		apiBO.setServiceId("3c.pay.querycarbycarno");
		apiBO.setRequestType("DATA");

		Map<String,String> param = new HashMap<String,String>();

		param.put("parkCodes", "000000223");

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
	public void queryCarInfoByCarno() throws JSApiException {
		// TODO Auto-generated method stub

	}

	@Override
	public void payByCarno() throws JSApiException {
		// TODO Auto-generated method stub

	}

}
