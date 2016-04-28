package com.disney.handler.jieshun;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.disney.exception.JSApiException;
import com.disney.handler.jieshun.api.ApiHandler;
import com.disney.handler.jieshun.api.JSApiRequestApiBO;
import com.disney.handler.jieshun.api.JSApiResultBO;
import com.disney.handler.jieshun.api.JSLoginBO;

@Service
public class JieShunServiceImpl implements JieShunService{

	@Override
	public String getLoginToken(String cid, String user, String password,String version) throws JSApiException {
		
		String url = "http://preapi.jslife.net/jsaims/login";	
		return  JieShunHandler.getLoginToken(cid, user, password,version, url);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> queryParkSpace() throws JSApiException {
	/*	JSApiResultBO execute = QueryParkSpace.execute(apiBO, loginBo);
		execute.getReturnObject();
		//修改返回数据类型
		Map<String, Object> returnObject = (Map<String, Object>) execute.getReturnObject();
		return returnObject;*/
		JSApiRequestApiBO apiBO = new JSApiRequestApiBO();
		
		apiBO.setServiceId("");
		apiBO.setRequestType("");
		
		Map<String,String> param = new HashMap<String,String>();
		
		
		param.put("", "");
		
		apiBO.setAttrs(param);
		
		
		JSLoginBO loginBo = new JSLoginBO();
		
		
		
		loginBo.setCid("");
		loginBo.setVersion("");
		loginBo.setLoginToken(getLoginToken("","","",""));
		
		
		
		
		
		
		JSApiResultBO result = ApiHandler.execute(apiBO, loginBo);
		
		
		
		return null;
		
		
	}
	
	/*@Override
	public Map<String,Object> queryCarByCarno(JSApiRequestApiBO apiBO) throws JSApiException {
		
		QueryParkSpace queryparkspace=new QueryParkSpace();
		JSApiResultBO execute = queryparkspace.execute(apiBO, null);
		execute.getReturnObject();
		return null;
	}

	@Override
	public void queryCarInfoByCarno(JSApiRequestApiBO apiBO) throws JSApiException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void payByCarno(JSApiRequestApiBO apiBO) throws JSApiException {
		// TODO Auto-generated method stub
		
	}*/

}
