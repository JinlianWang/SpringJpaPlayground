package com.springjpa.service;

import com.springjpa.model.core.Customer;
import com.springjpa.model.core.Order;
import com.springjpa.model.http.PurchaseOrderRequest;

public interface CustomerService {
	public Customer locateCustomer(String id);
	public Order makePurchase(String userId, Order request);
}
