package com.disney.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lotolo_step",catalog="disney")
public class LoToLoStep implements Serializable {
	private static final long serialVersionUID = 7037265390416976484L;
	
	private Long id;
	private Long loToLoId;
	private Integer type;
	private Integer step;
	private Long locationId;//如果为空表示 不是重要的途经点
	private String remark;
	
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
	public Long getLoToLoId() {
		return loToLoId;
	}
	
	public void setLoToLoId(Long loToLoId) {
		this.loToLoId = loToLoId;
	}
	
	@Column
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	
	@Column
	public Long getLocationId() {
		return locationId;
	}
	
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
