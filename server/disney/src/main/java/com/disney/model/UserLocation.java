package com.disney.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_location",catalog="disney")
public class UserLocation implements Serializable {
	private static final long serialVersionUID = 3279830157276840889L;

	private Long id;
	private String openId;
	
	private String scanLocation = "";
	
	private String parkLocation = "";
	private String leaveLocation = "";
	
	
	private Date createdAt = new Date();

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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Column
	public String getParkLocation() {
		return parkLocation;
	}
	public void setParkLocation(String parkLocation) {
		this.parkLocation = parkLocation;
	}
	
	@Column
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	@Column
	public String getLeaveLocation() {
		return leaveLocation;
	}
	public void setLeaveLocation(String leaveLocation) {
		this.leaveLocation = leaveLocation;
	}
	
	@Column
	public String getScanLocation() {
		return scanLocation;
	}
	
	public void setScanLocation(String scanLocation) {
		this.scanLocation = scanLocation;
	}

}
