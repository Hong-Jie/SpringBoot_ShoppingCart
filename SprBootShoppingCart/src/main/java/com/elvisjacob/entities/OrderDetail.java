package com.elvisjacob.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ORDER_DETAILS")
public class OrderDetail implements Serializable{

	private static final long serialVersionUID = 1550037604041152818L;

	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="PRICE")
	private double price;
	
	@Column(name="QUANTITY")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name="ORDER_ID", foreignKey = @ForeignKey(name="ORDER_DETAIL_ORD_FK"))
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID", foreignKey = @ForeignKey(name="ORDER_DETAIL_PROD_FK"))
	private Product product;

	public OrderDetail() {
		super();
	}

	public OrderDetail(String id, double amount, double price, int quantity, Order order, Product product) {
		super();
		this.id = id;
		this.amount = amount;
		this.price = price;
		this.quantity = quantity;
		this.order = order;
		this.product = product;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
