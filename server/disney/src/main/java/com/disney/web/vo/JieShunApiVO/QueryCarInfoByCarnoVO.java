package com.disney.web.vo.JieShunApiVO;

public class QueryCarInfoByCarnoVO {
	
	//车牌号
	private String carNo;
	
	//车辆是否在停车场
	private String inParkingState;
	
	//车辆所在停车场
	private String inParkingCode;
	
	//停车时长
	private String stopTime;
	
	//是否需要支付费用
	private double needPay;
	
	//停车应付费用
	private double charges;
	
	//停车已付费用
	private double paidFees;

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getInParkingState() {
		return inParkingState;
	}

	public void setInParkingState(String inParkingState) {
		this.inParkingState = inParkingState;
	}

	public String getInParkingCode() {
		return inParkingCode;
	}

	public void setInParkingCode(String inParkingCode) {
		this.inParkingCode = inParkingCode;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public double getNeedPay() {
		return needPay;
	}

	public void setNeedPay(double needPay) {
		this.needPay = needPay;
	}

	public double getCharges() {
		return charges;
	}

	public void setCharges(double charges) {
		this.charges = charges;
	}

	public double getPaidFees() {
		return paidFees;
	}

	public void setPaidFees(double paidFees) {
		this.paidFees = paidFees;
	}
}
