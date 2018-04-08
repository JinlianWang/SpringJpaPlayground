package com.springjpa.service;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.springjpa.model.db.CustomerDbEntity;
import com.springjpa.model.db.RoleDbEntity;
import com.springjpa.model.http.CustomerResponse;
import com.springjpa.model.http.RoleResponse;

@Component
public class CustomerDb2ReponseConverter extends ConversionServiceAwareConverter<CustomerDbEntity, CustomerResponse>{


	@Override
	public CustomerResponse convert(CustomerDbEntity source) {
		CustomerResponse response = new CustomerResponse(source.getSso_id());
		response.setFirstName(source.getFirstName());
		response.setLastName(source.getLastName());

		HashSet<RoleResponse> set = new HashSet<RoleResponse>();
		for(RoleDbEntity role : source.getRoles()) {
			set.add(conversionService.convert(role, RoleResponse.class));
		}
		response.setRoles(set);
		return response;
	}

}
