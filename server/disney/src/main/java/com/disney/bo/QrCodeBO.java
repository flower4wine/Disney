package com.disney.bo;


public class QrCodeBO {
	
	private String qrode;
	private String qrLocationName;
	
	private String region;
	private String codeRange;
	private boolean nearEntra=false;
	private String size;
	private String style;
	
	private String locationImg;
	
	public String getLocationImg() {
		return locationImg;
	}
	public void setLocationImg(String locationImg) {
		this.locationImg = locationImg;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCodeRange() {
		return codeRange;
	}
	public void setCodeRange(String codeRange) {
		this.codeRange = codeRange;
	}
	public boolean isNearEntra() {
		return nearEntra;
	}
	public void setNearEntra(boolean nearEntra) {
		this.nearEntra = nearEntra;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getQrode() {
		return qrode;
	}
	public void setQrode(String qrode) {
		this.qrode = qrode;
	}
	public String getQrLocationName() {
		return qrLocationName;
	}
	public void setQrLocationName(String qrLocationName) {
		this.qrLocationName = qrLocationName;
	}
}
