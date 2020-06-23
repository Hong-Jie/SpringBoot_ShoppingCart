package com.elvisjacob.form;

import com.elvisjacob.model.CustomerInfo;

public class CustomerForm {

	private String name;
	private String addr;
	private String email;
	private String phone;
	
	private boolean valid;

	public CustomerForm() {
	}

	public CustomerForm(CustomerInfo customerInfo) {
		if (customerInfo != null) {
			this.name = customerInfo.getName();
			this.addr = customerInfo.getAddr();
			this.email = customerInfo.getEmail();
			this.phone = customerInfo.getPhone();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
