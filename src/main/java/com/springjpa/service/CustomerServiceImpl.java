package com.springjpa.service;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.springjpa.model.db.CustomerDbEntity;
import com.springjpa.model.db.RoleDbEntity;
import com.springjpa.model.http.CustomerResponse;
import com.springjpa.model.http.RoleResponse;
import com.springjpa.repo.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository repository;
	
	@Autowired
    private ConversionService conversionService;
	
	@Override
	@Transactional
	public CustomerResponse locateCustomer(String id) {
		CustomerDbEntity one = repository.findOne(id);
		CustomerResponse response = convertToCustomerResponse(one);
		return response;
	}

	private CustomerResponse convertToCustomerResponse(CustomerDbEntity one) {
		CustomerResponse response = conversionService.convert(one, CustomerResponse.class);
		HashSet<RoleResponse> set = new HashSet<RoleResponse>();
		for(RoleDbEntity role : one.getRoles()) {
			set.add(conversionService.convert(role, RoleResponse.class));
		}
		response.setRoles(set);
		return response;
	}

}
