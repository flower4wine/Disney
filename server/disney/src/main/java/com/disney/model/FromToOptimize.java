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
	
	
	
	

}
