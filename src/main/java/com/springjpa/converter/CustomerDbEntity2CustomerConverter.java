package com.springjpa.converter;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.springjpa.model.core.Customer;
import com.springjpa.model.core.Role;
import com.springjpa.model.db.CustomerDbEntity;
import com.springjpa.model.db.RoleDbEntity;

@Component
public class CustomerDbEntity2CustomerConverter extends ConversionServiceAwareConverter<CustomerDbEntity, Customer>{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Customer convert(CustomerDbEntity source) {
		Customer response = new Customer(source.getId());
		response.setFirstName(source.getFirstName());
		response.setLastName(source.getLastName());

		logger.info("adding roles now!");
		
		HashSet<Role> set = new HashSet<Role>();
		for(RoleDbEntity role : source.getRoles()) {
			set.add(conversionService.convert(role, Role.class));
		}
		response.setRoles(set);
		
		return response;
	}

}
