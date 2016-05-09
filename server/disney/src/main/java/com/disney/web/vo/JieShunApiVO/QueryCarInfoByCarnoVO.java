package com.disney.web.vo.JieShunApiVO;

public class QueryCarInfoByCarnoVO {
	
	//车牌号
	private String carNo;
	
	//车辆是否在停车场
	private boolean inParkingState;
	
	//车辆所在停车场
	private String inParkingCode;
	
	//停车时长
	private String stopTime;
	
	//是否需要支付费用
	private boolean needPay = false;
	
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

	public boolean isInParkingState() {
		return inParkingState;
	}

	public void setInParkingState(boolean inParkingState) {
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

	public boolean isNeedPay() {
		return needPay;
	}

	public void setNeedPay(boolean needPay) {
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
