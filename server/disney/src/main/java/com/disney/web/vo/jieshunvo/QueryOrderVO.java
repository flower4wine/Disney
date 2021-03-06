package com.disney.web.vo.jieshunvo;


public class QueryOrderVO {
	
	//订单编号
	private String orderNo="00001";
	
	//车牌号
	private String carNo="测12345";
	
	//入场时间
	private String startTime="2016-05-11 9:56";
	
	//停留时间
	private String serviceTime="3小时24分钟";
	
	//应付费用
	private double serviceFee=10;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

}
