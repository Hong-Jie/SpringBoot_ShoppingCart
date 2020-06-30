package com.elvisjacob.model;

import java.util.ArrayList;
import java.util.List;

public class CartInfo {

	private int orderNum;
	private CustomerInfo customerInfo;
	private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

	public CartInfo() {
	}
	
	
	public int getOrderNum() {
		return orderNum;
	}



	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}



	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}



	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}



	public List<CartLineInfo> getCartLines() {
		return cartLines;
	}
	
	
	private CartLineInfo findLineByCode(String code) {
		for (CartLineInfo line: cartLines) {
			if (line.getProductInfo().getCode().equals(code)) {
				return line;
			}
		}
		return null;
	}
	
	private void updateProduct(CartLineInfo line, int quantity) {
		assert line != null: "Updating empty product!";
		
		if (quantity<=0) {
			this.cartLines.remove(line);
		} else {
			line.setQuantity(quantity);
		}
	}
	
	
	public void addProduct(ProductInfo productInfo, int quantity) {
		CartLineInfo line = this.findLineByCode(productInfo.getCode());
		if (line == null) {
			line = new CartLineInfo(productInfo);
			this.cartLines.add(line);
		}
		
		int newQuantity = line.getQuantity()+quantity;
		this.updateProduct(line, newQuantity);
	}
	
	public void removeProduct(ProductInfo productInfo) {
		CartLineInfo line = this.findLineByCode(productInfo.getCode());
		
		if (line != null) {
			this.cartLines.remove(line);
		}
	}
	
	public boolean isEmpty() {
		return this.cartLines.isEmpty();
	}
	
	// TODO
	public void validate() {}
	
	
	public boolean isValidCustomer() {
		return this.customerInfo != null && this.customerInfo.isValid();
	}

	
	public int getQuantityTotal() {
		int quantity = 0;
		for (CartLineInfo line: cartLines) {
			quantity += line.getQuantity();
		}
		return quantity;
	}
	
	public double getAmountTotal() {
		double amount = 0;
		for (CartLineInfo line: cartLines) {
			amount += line.getAmount();
		}
		return amount;
	}
	
	public void updateQuantity(CartInfo cartForm) {
		if (cartForm != null) {
			List<CartLineInfo> lines = cartForm.getCartLines();
			for (CartLineInfo newLine: lines) {
				CartLineInfo line = this.findLineByCode(newLine.getProductInfo().getCode());
				if (line != null) {
					this.updateProduct(line, newLine.getQuantity());
				}
			}
		}
	}
	
}
