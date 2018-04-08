package com.springjpa.service;

import com.springjpa.model.http.CustomerResponse;

public interface CustomerService {
	CustomerResponse locateCustomer(String id);
}
