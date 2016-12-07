package com.cmu.edu.ebiz.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "MEETING")
public class Meeting implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4166301168581509090L;

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name = "IDROOM")
	private Integer idRoom;
	
	@Column(name = "IDGROUP")
	private Integer idGroup;
	
	@Column(name = "ANDREWID")
	private String andrewId;
	
	@Column(name = "STARTTIME")
	private Date start;
	
	@Column(name = "ENDTIME")
	private Date end;
	
	@Column(name = "ISCHECKIN")
	private boolean isCheckIn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title != null) {
			title = title.replaceAll("<", "").replaceAll(">", "");
			title = title.replaceAll("eval\\((.*)\\)", "");
			title = title.replaceAll("[\\\"\\\'][\\s]*((?i)javascript):(.*)[\\\"\\\']", "\"\"");
			title = title.replaceAll("((?i)script)", "");
		}
		this.title = title;
	}

	public Integer getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(Integer idRoom) {
		this.idRoom = idRoom;
	}

	public Integer getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}

	public String getAndrewId() {
		return andrewId;
	}

	public void setAndrewId(String andrewId) {
		this.andrewId = andrewId;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public boolean getIsCheckIn() {
		return isCheckIn;
	}

	public void setIsCheckIn(boolean isCheckIn) {
		this.isCheckIn = isCheckIn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
