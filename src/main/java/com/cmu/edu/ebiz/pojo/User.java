package com.cmu.edu.ebiz.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

@Entity
@Table(name = "USER")
public class User implements Serializable {
	
	public static final int USER_ADMIN = 0;
	public static final int USER_STUDENT = 1;
	public static final String defaultPW = "111111";

	private static final long serialVersionUID = -6827022180103688695L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer id;
	
	@Column(name = "ANDREWID", unique = true)
	private String andrewId;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "FIRSTNAME")
	private String firstname;
	
	@Column(name = "LASTNAME")
	private String lastname;
	
	@Column(name = "TYPE")
	private Integer type;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GROUP_ID", nullable = true)
    private Group group;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAndrewId() {
		return andrewId;
	}

	public void setAndrewId(String andrewId) {
		this.andrewId = andrewId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static boolean checkUser(User user) {
		if (user.getAndrewId() == null || "".equals(user.getAndrewId().trim())) {
			return false;
		} else if (user.getFirstname() == null || "".equals(user.getFirstname().trim())) {
			return false;
		} else if (user.getLastname() == null || "".equals(user.getLastname().trim())) {
			return false;
		} else if (user.getPassword() == null || "".equals(user.getPassword().trim())) {
			return false;
		}
		return true;
	}
}
