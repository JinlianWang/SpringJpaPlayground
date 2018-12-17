package com.springjpa.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.springjpa.model.core.Order;
import com.springjpa.model.db.CustomerDbEntity;
import com.springjpa.model.db.OrderDbEntity;
import com.springjpa.model.db.RoleDbEntity;
import com.springjpa.model.http.CustomerResponse;
import com.springjpa.model.http.PurchaseOrderRequest;
import com.springjpa.repo.CustomerRepository;
import com.springjpa.repo.OrderRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository repository;
	
	@Autowired
	OrderRepository orderRepository;
	
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
	
	@Override
	@Transactional
	public Order makePurchase(String userId, Order purchaseOrderRequest) {
		CustomerDbEntity customerEntity = repository.findOne(userId);
		OrderDbEntity orderEntity = conversionService.convert(purchaseOrderRequest, OrderDbEntity.class);
		orderEntity.setCustomer(customerEntity);
		orderRepository.save(orderEntity);
		Order order = conversionService.convert(orderEntity, Order.class);
		return order; 
	}
}
