package com.springjpa.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.springjpa.model.db.CustomerDbEntity;
import com.springjpa.model.db.RoleDbEntity;
import com.springjpa.model.http.CustomerResponse;
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
		for(RoleDbEntity role: one.getRoles()) {
			role.setPermission(repository.retrievePermissions(role.getRoleId()));
		}
		CustomerResponse response = conversionService.convert(one, CustomerResponse.class);
		return response;
	}
}
