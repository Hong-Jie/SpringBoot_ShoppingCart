package com.elvisjacob.model;

public class ProductInfo {

	private String code;
	private String name;
	private double price;
	
	public ProductInfo() {
		super();
	}

	public ProductInfo(String code, String name, double price) {
		super();
		this.code = code;
		this.name = name;
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductInfo [code=" + code + ", name=" + name + ", price=" + price + "]";
	}
	
}
