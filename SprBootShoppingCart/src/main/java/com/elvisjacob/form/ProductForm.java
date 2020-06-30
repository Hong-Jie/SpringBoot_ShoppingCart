package com.elvisjacob.form;

import com.elvisjacob.entities.Product;

public class ProductForm {
	
	private String code;
	private String name;
	private double price;
	private String imagePath;

	private boolean newProduct;

	public ProductForm() {
	}

	public ProductForm(Product product) {
		this.code = product.getCode();
		this.name = product.getName();
		this.price = product.getPrice();
		this.imagePath = product.getImage();
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

	public boolean isNewProduct() {
		return newProduct;
	}

	public void setNewProduct(boolean newProduct) {
		this.newProduct = newProduct;
	}
	
}
