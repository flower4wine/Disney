package com.disney.web.vo;

public class ParkVO {
	
	private String qrCode;
	
	private Integer totalSpace;
	
	private Integer restSpace;
	
	private String parkName;
	
	private String jsCode;
	
	private Integer status;
	
	private Integer price;
	
	public Integer getTotalSpace() {
		return totalSpace;
	}

	public void setTotalSpace(Integer totalSpace) {
		this.totalSpace = totalSpace;
	}

	public Integer getRestSpace() {
		return restSpace;
	}

	public void setRestSpace(Integer restSpace) {
		this.restSpace = restSpace;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getQrCode() {
		return qrCode;
	}
	
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	
	public String getJsCode() {
		return jsCode;
	}
	
	public void setJsCode(String jsCode) {
		this.jsCode = jsCode;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}

}
