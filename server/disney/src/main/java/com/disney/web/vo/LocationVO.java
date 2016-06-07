package com.disney.web.vo;

public class LocationVO {
	
	private String name;
	private String code;
	private String remark;
	private String locationImg;
	private boolean isParkLocation = true;
	
	public boolean isParkLocation() {
		return isParkLocation;
	}
	public void setParkLocation(boolean isParkLocation) {
		this.isParkLocation = isParkLocation;
	}
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

}
