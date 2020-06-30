package com.elvisjacob.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ORDERS")
public class Order implements Serializable{

	private static final long serialVersionUID = -5682569057139277855L;

	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="CUSTOMER_ADDRESS")
	private String addr;
	
	@Column(name="CUSTOMER_EMAIL")
	private String email;
	
	@Column(name="CUSTOMER_NAME")
	private String name;
	
	@Column(name="CUSTOMER_PHONE")
	private String phone;
	
	@Column(name="ORDER_DATE")
	private Date orderDate;
	
	@Column(name="ORDER_NUM")
	private int orderNum;

	public Order() {
		super();
	}

	public Order(String id, double amount, String addr, String email, String name, String phone, Date orderDate,
			int orderNum) {
		super();
		this.id = id;
		this.amount = amount;
		this.addr = addr;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.orderDate = orderDate;
		this.orderNum = orderNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	
	
}
