package com.springjpa.service;

import com.springjpa.model.db.CustomerDbEntity;
import com.springjpa.model.http.CustomerResponse;
import org.springframework.core.convert.converter.Converter;

public class CustomerDb2ReponseConverter implements Converter<CustomerDbEntity, CustomerResponse>{


	@Override
	public CustomerResponse convert(CustomerDbEntity source) {
		CustomerResponse response = new CustomerResponse(source.getSso_id());
		response.setFirstName(source.getFirstName());
		response.setLastName(source.getLastName());
		return response;
	}

}
