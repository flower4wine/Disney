package com.disney.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.disney.constant.Lo2LoType;

@Entity
@Table(name="lotolo",catalog="disney")
public class LoToLo  implements Serializable {
	
	private static final long serialVersionUID = -4359532089918930164L;
	
	private Long id;
	private String fromQrCode;
	private String toQrCode;
	
	private String innerUrl;
	private String outUrl;
	
	private String time;
	private String distince;
	
	private Integer lo2loType = Lo2LoType.PARKINNER_2_VIEW;
	
    @Id
  	@GeneratedValue(strategy = IDENTITY)
  	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getFromQrCode() {
		return fromQrCode;
	}
	public void setFromQrCode(String fromQrCode) {
		this.fromQrCode = fromQrCode;
	}
	
	@Column
	public String getToQrCode() {
		return toQrCode;
	}
	public void setToQrCode(String toQrCode) {
		this.toQrCode = toQrCode;
	}
	
	@Column
	public String getInnerUrl() {
		return innerUrl;
	}
	public void setInnerUrl(String innerUrl) {
		this.innerUrl = innerUrl;
	}
	
	@Column
	public String getOutUrl() {
		return outUrl;
	}
	
	@Column
	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}
	public String getTime() {
		return time;
	}
	
	@Column
	public void setTime(String time) {
		this.time = time;
	}
	
	@Column
	public String getDistince() {
		return distince;
	}
	
	public void setDistince(String distince) {
		this.distince = distince;
	}
	
	@Column
	public Integer getLo2loType() {
		return lo2loType;
	}
	
	public void setLo2loType(Integer lo2loType) {
		this.lo2loType = lo2loType;
	}

}
