package com.disney.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="qr_code")
public class QrCode implements Serializable {

	private static final long serialVersionUID = 6241861776870818281L;
	
	private Long id;
	private String qrCode;
	private Long locationId;
	
	private String orderNo;
	private String qrUrl;
	private Integer qrCodeType;
	
	private Boolean nearEntra = false;
	
	private String region;
	private String codeRange;
	private String size;
	private String style;
	
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
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	
	@Column
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	
	@Column
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column
	public String getQrUrl() {
		return qrUrl;
	}
	

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}
	
	@Column
	public Integer getQrCodeType() {
		return qrCodeType;
	}
	public void setQrCodeType(Integer qrCodeType) {
		this.qrCodeType = qrCodeType;
	}
	
	@Column
	public Boolean getNearEntra() {
		return nearEntra;
	}
	public void setNearEntra(Boolean nearEntra) {
		this.nearEntra = nearEntra;
	}
	
	@Column
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	@Column
	public String getCodeRange() {
		return codeRange;
	}
	public void setCodeRange(String codeRange) {
		this.codeRange = codeRange;
	}
	
	@Column
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@Column
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
}
