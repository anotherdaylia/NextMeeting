package com.cmu.edu.ebiz.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROOM")
public class Room implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3025392091196622421L;

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer id;
	
	@Column(name = "Name", unique = true)
	private String name;

	@Column(name = "isActive")
	private boolean isActive;

	private String isActiveString;

	public String getIsActiveString() {
		return isActiveString;
	}

	public void setIsActiveString(String isActiveString) {
		this.isActiveString = isActiveString;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
