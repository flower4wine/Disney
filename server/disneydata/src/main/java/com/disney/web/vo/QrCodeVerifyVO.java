package com.disney.web.vo;

import java.util.Map;

import com.disney.util.ObjectMapUtil;

public class QrCodeVerifyVO {
	
	private String name;
	private String region;
	private String range;
	private String code;
	private String size;
	private String style;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	
	public static QrCodeVerifyVO getVO(Map<String,String> map){
		
		if(map!=null){
			QrCodeVerifyVO vo = new QrCodeVerifyVO();
			ObjectMapUtil.setValue(map, vo);
			return vo;
		}
		
		return null;
		
	}
	
}
