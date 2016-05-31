package com.disney.web.vo;

import java.util.Map;

import com.disney.model.QrCode;
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

	public static QrCodeVerifyVO getVO(QrCode code){

		if(code!=null){
			QrCodeVerifyVO vo = new QrCodeVerifyVO();
			
			String qrCode = code.getQrCode();
			
			if(qrCode.startsWith("03-0001")){
				vo.setName("P1");
			}else if(qrCode.startsWith("03-0002")){
				vo.setName("P2");
			}else if(qrCode.startsWith("03-0003")){
				vo.setName("P3");
			}else{
				vo.setName("P4");
			}
			
			vo.setCode(code.getQrCode());
			vo.setRange(code.getCodeRange());
			vo.setSize(code.getSize());
			vo.setStyle(code.getStyle());
			vo.setRegion(code.getRegion());
			
			
			return vo;
		}

		return null;

	}

}
