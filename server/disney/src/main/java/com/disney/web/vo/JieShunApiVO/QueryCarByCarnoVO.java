package com.disney.web.vo.JieShunApiVO;

public class QueryCarByCarnoVO {
	
	//车牌号
	private String carNo;
	
	//是否在停车场
	private String inParkingState;
	
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

	public String getInParkingState() {
		return inParkingState;
	}

	public void setInParkingState(String inParkingState) {
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
