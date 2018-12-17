package com.springjpa.service;

import com.springjpa.model.core.Order;
import com.springjpa.model.http.CustomerResponse;
import com.springjpa.model.http.PurchaseOrderRequest;

public interface CustomerService {
	CustomerResponse locateCustomer(String id);
	Order makePurchase(String userId, Order request);
}
