package com.disney.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="from_to",catalog="disney")
public class FromToOptimize  implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -448396137530128339L;
	
	private Long id;
	private String fromCode;
	private String toCode;
	private Boolean bus;
	private String fromBus;
	private String toBus;
	private Boolean inside;
	
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
	public String getFromCode() {
		return fromCode;
	}
	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}
	
	@Column
	public String getToCode() {
		return toCode;
	}
	public void setToCode(String toCode) {
		this.toCode = toCode;
	}
	
	@Column
	public Boolean getBus() {
		return bus;
	}
	public void setBus(Boolean bus) {
		this.bus = bus;
	}
	
	@Column
	public String getFromBus() {
		return fromBus;
	}
	public void setFromBus(String fromBus) {
		this.fromBus = fromBus;
	}
	
	@Column
	public String getToBus() {
		return toBus;
	}
	public void setToBus(String toBus) {
		this.toBus = toBus;
	}
	
	@Column
	public Boolean getInside() {
		return inside;
	}
	public void setInside(Boolean inside) {
		this.inside = inside;
	}

}
