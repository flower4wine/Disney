package com.disney.handler.jieshun;

import java.util.Map;

import com.disney.exception.JSApiException;

public interface JieShunService {

	public String getLoginToken(String cid, String user, String password,String version) throws JSApiException ;
	
	/**
	 * 查询停车库车位使用情况
	 * @return
	 * @throws JSApiException
	 */
	public Map<String,Object> queryParkSpace() throws JSApiException;
	
	/**
	 * 根据车牌号查询停车位置
	 * @param carNo
	 * @return
	 * @throws JSApiException
	 */
	public Map<String,Object> queryCarStopByCarno(String carNo) throws JSApiException;
	
	/**
	 * 根据车牌查询车辆停车信息
	 * @param carNo
	 * @return
	 * @throws JSApiException
	 */
	public Map<String,Object> queryCarInfoByCarno(String carNo) throws JSApiException;

	/**
	 * 根据车牌号支付
	 * @param carNo
	 * @return
	 * @throws JSApiException
	 */
	public Map<String,Object> payByCarno(String carNo) throws JSApiException;
	
	/**
	 * 根据车牌号创建停车订单
	 * @param carNo
	 * @return
	 * @throws JSApiException
	 */
	public String createOrderByCarno(String carNo) throws JSApiException;
	
	/**
	 * 查询订单
	 * @param orderNo
	 * @return
	 * @throws JSApiException
	 */
	public Map<String,Object> queryOrder(String orderNo) throws JSApiException;

	/**
	 * 根据车牌号查询订单
	 * @param carNo
	 * @return
	 * @throws JSApiException
	 */
	public Map<String, Object> queryOrderByCarNo(String carNo) throws JSApiException;
}
