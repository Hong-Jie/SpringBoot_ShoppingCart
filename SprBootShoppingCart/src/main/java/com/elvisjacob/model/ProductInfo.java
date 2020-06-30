package com.elvisjacob.model;

import com.elvisjacob.entities.Product;

public class ProductInfo {

	@Override
	public String toString() {
		return "ProductInfo [code=" + code + ", name=" + name + ", imagePath=" + imagePath + ", price=" + price + "]";
	}

	private String code;
	private String name;
	private String imagePath;
	private double price;
	
	public ProductInfo() {
	}

	public ProductInfo(Product product) {
		this.code = product.getCode();
		this.name = product.getName();
		this.imagePath = product.getImage();
		this.price = product.getPrice();
	}
	
	public ProductInfo(String code, String name, String imagePath, double price) {
		this.code = code;
		this.name = name;
		this.imagePath = imagePath;
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
