package com.disney.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="location",catalog="disney")
public class Location  implements java.io.Serializable {

	private static final long serialVersionUID = 6812816107766481337L;
	
	private Long id;
	private String location;
	private String name;
	private Integer type;
	private Boolean qrCodeLocation = false;
	
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column
	public Boolean getQrCodeLocation() {
		return qrCodeLocation;
	}
	public void setQrCodeLocation(Boolean qrCodeLocation) {
		this.qrCodeLocation = qrCodeLocation;
	}
	
	

}
