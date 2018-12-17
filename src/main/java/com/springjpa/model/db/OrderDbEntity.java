package com.springjpa.model.db;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderDbEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private String orderId;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	@JoinColumn(name="user_id", referencedColumnName = "sso_id")
	private CustomerDbEntity customer;
	
	@Column(name="amount")
	private double amount;
	
	@Column(name="product")
	private String product;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public CustomerDbEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDbEntity customer) {
		this.customer = customer;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	
	protected OrderDbEntity() {
	}
	 
	public OrderDbEntity(double amount, String product) {
		this.orderId = UUID.randomUUID().toString();
		this.amount = amount;
		this.product = product;
	}

}
