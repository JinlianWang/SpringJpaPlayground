package com.springjpa.service;

import com.springjpa.model.Customer;

public interface CustomerService {
	Customer locateCustomer(String id);
}
