package com.springjpa.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.model.Customer;
import com.springjpa.repo.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository repository;
	
	@Override
	@Transactional
	public Customer locateCustomer(String id) {
		Customer one = repository.findOne(id);
		return one;
	}

}
