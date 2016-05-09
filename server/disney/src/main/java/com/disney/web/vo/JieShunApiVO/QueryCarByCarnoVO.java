package com.disney.web.vo.jieshunapivo;

public class QueryCarByCarnoVO {
	
	//车牌号
	private String carNo;
	
	//是否在停车场
	private boolean inParkingState = false;
	
	//停车场编号
	private String parkingCode;
	
	//停车场名称
	private String parkingName;

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

	public String getParkingCode() {
		return parkingCode;
	}

	public void setParkingCode(String parkingCode) {
		this.parkingCode = parkingCode;
	}

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
}
