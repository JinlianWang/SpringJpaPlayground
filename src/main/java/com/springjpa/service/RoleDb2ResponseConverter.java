package com.springjpa.service;

import org.springframework.core.convert.converter.Converter;
import com.springjpa.model.db.RoleDbEntity;
import com.springjpa.model.http.RoleResponse;

public class RoleDb2ResponseConverter implements Converter<RoleDbEntity, RoleResponse>{

	@Override
	public RoleResponse convert(RoleDbEntity source) {
		RoleResponse response = new RoleResponse(source.getRoleId());
		response.setName(source.getName());
		return response;
	}
}