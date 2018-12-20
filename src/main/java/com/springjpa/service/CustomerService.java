package com.springjpa.service;

import com.springjpa.model.core.Customer;
import com.springjpa.model.core.Order;
import com.springjpa.model.http.PurchaseOrderRequest;

public interface CustomerService {
	Customer locateCustomer(String id);
	Order makePurchase(String userId, Order request);
}
