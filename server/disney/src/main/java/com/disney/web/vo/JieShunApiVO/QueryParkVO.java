package com.disney.web.vo.jieshunapivo;

public class QueryParkVO {
	
	//停车场编号
	private String parkCode;
	
	//停车场名称
	private String parkingName;
	
	//空余车位
	private int restSpace;
	
	//总车位
	private int totalSpace;
	
	//停车单价
	private double price;
	
	//停车场状态(0=true,1=false)
	private int parkingState = 0;
	
	public String getParkCode() {
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}
	public String getParkingName() {
		return parkingName;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public int getRestSpace() {
		return restSpace;
	}
	public void setRestSpace(int restSpace) {
		this.restSpace = restSpace;
	}
	public int getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(int totalSpace) {
		this.totalSpace = totalSpace;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getParkingState() {
		return parkingState;
	}
	public void setParkingState(int parkingState) {
		this.parkingState = parkingState;
	}
}
