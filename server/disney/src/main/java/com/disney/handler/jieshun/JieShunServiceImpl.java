package com.disney.handler.jieshun;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disney.dao.LocationDao;
import com.disney.exception.JSApiException;
import com.disney.handler.jieshun.api.ApiHandler;
import com.disney.handler.jieshun.api.JSApiRequestApiBO;
import com.disney.handler.jieshun.api.JSApiResultBO;
import com.disney.handler.jieshun.api.JSLoginBO;
import com.disney.handler.jieshun.constant.LoginUser;
import com.disney.model.Location;
import com.disney.web.vo.JieShunApiVO.QueryCarByCarnoVO;
import com.disney.web.vo.JieShunApiVO.QueryCarInfoByCarnoVO;
import com.disney.web.vo.JieShunApiVO.QueryOrderVO;
import com.disney.web.vo.JieShunApiVO.QueryParkVO;

@Service
public class JieShunServiceImpl implements JieShunService{
	@Autowired
	private LocationDao lo;
	
	@Override
	public String getLoginToken(String cid, String user, String password,String version) throws JSApiException {

		String url = "http://preapi.jslife.net/jsaims/login";	
		return  JieShunHandler.getLoginToken(cid, user, password,version, url);

	}


	@Override
	public List<QueryParkVO> queryParkSpace() throws JSApiException {
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
		
		return mapToQueryParkVO(json);

	}
	
	@SuppressWarnings("unchecked")
	private List<QueryParkVO> mapToQueryParkVO(Map<String, Object> map){
		List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("dataItems");
		List<QueryParkVO> voList = new ArrayList<QueryParkVO>();
		for(Map<String, Object> park:list){
			Map<String,Object> attrs = (Map<String,Object>) park.get("attributes");
			QueryParkVO vo = new QueryParkVO();
			vo.setTotalSpace(Integer.valueOf(attrs.get("totalSpace").toString()));
			vo.setParkingName((String) attrs.get("parkName"));
			vo.setRestSpace(Integer.valueOf(attrs.get("restSpace").toString()));
			
			String parkCode = (String) attrs.get("parkCode");
			if(parkCode.equals("0000002236")){
				vo.setParkCode("03-0003");
			}
			voList.add(vo);
			System.out.println(attrs);
		}
		return voList;
	}

	@Override
	public List<QueryCarByCarnoVO> queryCarStopByCarno(String carNo) throws JSApiException {
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
		return mapToQueryCarByCarnoVO(json);
	}
	
	@SuppressWarnings("unchecked")
	private List<QueryCarByCarnoVO> mapToQueryCarByCarnoVO(Map<String, Object> map){
		List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("dataItems");
		List<QueryCarByCarnoVO> voList = new ArrayList<QueryCarByCarnoVO>();
		for(Map<String, Object> car:list){
			Map<String,Object> attrs = (Map<String,Object>) car.get("attributes");
			QueryCarByCarnoVO vo = new QueryCarByCarnoVO();
			if(attrs.isEmpty() || attrs.size() <= 0){
				continue;
			}
			String parkingCode = (String) attrs.get("parkPlaceCode");
			if(parkingCode.equals("0000002236")){
				vo.setParkingCode("03-0001");
			}
			Location location = lo.find(vo.getParkingCode());
			vo.setParkingName(location.getName());
			vo.setCarNo((String) attrs.get("carNo"));
			vo.setInParkingState(true);
			voList.add(vo);
			System.out.println(attrs);
		}
		return voList;
	}

	@Override
	public List<QueryCarInfoByCarnoVO> queryCarInfoByCarno(String carNo) throws JSApiException {
		
		Map<String, Object> querryOrderByCarNo = this.queryOrderByCarNo(carNo);
		
		return mapToQueryCarInfoByCarnoVO(querryOrderByCarNo);
		
	}
	
	@SuppressWarnings("unchecked")
	private List<QueryCarInfoByCarnoVO> mapToQueryCarInfoByCarnoVO(Map<String, Object> map){
		List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("dataItems");
		List<QueryCarInfoByCarnoVO> voList = new ArrayList<QueryCarInfoByCarnoVO>();
		for(Map<String, Object> car:list){
			Map<String,Object> attrs = (Map<String,Object>) car.get("attributes");
			QueryCarInfoByCarnoVO vo = new QueryCarInfoByCarnoVO();
			if(attrs.isEmpty() || attrs.size() <= 0){
				continue;
			}
			
			vo.setInParkingState(true);
			String inParkingCode = (String) attrs.get("parkCode");
			vo.setInParkingCode(inParkingCode);
			vo.setCarNo((String) attrs.get("carNo"));
			vo.setStopTime(attrs.get("serviceTime").toString());
			Double charges = Double.valueOf(attrs.get("serviceFee").toString());
			vo.setCharges(charges);
			Double paidFees = Double.valueOf(attrs.get("totalFee").toString());
			vo.setPaidFees(paidFees);
			if(new BigDecimal(paidFees).compareTo(new BigDecimal(charges)) < 0){
				vo.setNeedPay(true);
			}
			voList.add(vo);
			System.out.println(attrs);
		}
		return voList;
	}

	@Override
	public List<QueryOrderVO> queryOrderByCarNo(String carNo) throws JSApiException {
		Map<String, Object> queryOrder = new HashMap<String,Object>();
		List<QueryCarByCarnoVO> queryCarStopByCarno = this.queryCarStopByCarno(carNo);
		if(!queryCarStopByCarno.isEmpty() && queryCarStopByCarno.size() > 0){
			String orderNo = this.createOrderByCarno(carNo);
			queryOrder =  this.queryOrder(orderNo);
		}
		return mapToQueryOrderVO(queryOrder);
	}
	
	@SuppressWarnings("unchecked")
	private List<QueryOrderVO> mapToQueryOrderVO(Map<String, Object> map){
		List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("dataItems");
		List<QueryOrderVO> voList = new ArrayList<QueryOrderVO>();
		for(Map<String, Object> car:list){
			Map<String,Object> attrs = (Map<String,Object>) car.get("attributes");
			QueryOrderVO vo = new QueryOrderVO();
			if(attrs.isEmpty() || attrs.size() <= 0){
				continue;
			}
			
			
			voList.add(vo);
			System.out.println(attrs);
		}
		return voList;
	}

	@Override
	public Map<String,Object> payByCarno(String carNo) throws JSApiException {
		
		List<QueryCarByCarnoVO> queryCarStopByCarno = this.queryCarStopByCarno(carNo);
		if(!queryCarStopByCarno.isEmpty() && queryCarStopByCarno.size()>0){
			String orderNo = this.createOrderByCarno(carNo);
			Map<String, Object> queryOrder = this.queryOrder(orderNo);
			Double serviceFee = (Double) queryOrder.get("serviceFee");
			//添加微信支付
		}
		
		return null;
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
