package com.elvisjacob.model;

public class CartLineInfo {

	private ProductInfo productInfo;
	private int quantity;
	
	public CartLineInfo() {
	}

	public CartLineInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
		this.quantity = 0;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return this.productInfo.getPrice() * this.quantity;
	}

	@Override
	public String toString() {
		return "CartLineInfo [productInfo=" + productInfo + ", quantity=" + quantity + "]";
	}

	
}
