package com.springjpa.service;

import com.springjpa.model.db.CustomerDbEntity;
import com.springjpa.model.db.RoleDbEntity;
import com.springjpa.model.http.CustomerResponse;
import com.springjpa.model.http.RoleResponse;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

public class CustomerDb2ReponseConverter implements Converter<CustomerDbEntity, CustomerResponse>{

//	@Autowired
//    private ConversionService conversionService;
	
	@Override
	public CustomerResponse convert(CustomerDbEntity source) {
		CustomerResponse response = new CustomerResponse(source.getSso_id());
		response.setFirstName(source.getFirstName());
		response.setLastName(source.getLastName());
		HashSet<RoleResponse> set = new HashSet<RoleResponse>();
		for(RoleDbEntity role : source.getRoles()) {
//			set.add(conversionService.convert(role, RoleResponse.class));
		}
		return response;
	}

}
