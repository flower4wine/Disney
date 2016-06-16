package com.disney.web.vo;

public class LocationVO {
	
	private String name;
	private String code;
	private String remark;
	private String locationImg;
	private Boolean parkLocation = true;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLocationImg() {
		return locationImg;
	}
	public void setLocationImg(String locationImg) {
		this.locationImg = locationImg;
	}
	public Boolean getParkLocation() {
		return parkLocation;
	}
	public void setParkLocation(Boolean parkLocation) {
		this.parkLocation = parkLocation;
	}

}
