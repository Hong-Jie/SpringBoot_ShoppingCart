package com.elvisjacob.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACCOUNTS")
public class Account implements Serializable{

	private static final long serialVersionUID = -4926764137234642038L;
	
	public static final String ROLE_MANAGER = "ROLE_MANAGER";
	public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
	
	@Id
	@Column(name="USER_NAME")
	private String username;
	
	@Column(name="ACTIVE")
	private boolean active;

	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="USER_ROLE")
	private String role;

	public Account() {
		super();
	}

	public Account(String username, boolean active, String password, String role) {
		super();
		this.username = username;
		this.active = active;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Account [username=" + username + ", active=" + active + ", password=" + password + ", role=" + role
				+ "]";
	}
	
	
	
}
