package com.springjpa.converter;

import org.springframework.stereotype.Component;

import com.springjpa.model.db.RoleDbEntity;
import com.springjpa.model.http.RoleResponse;

@Component
public class RoleDb2ResponseConverter extends ConversionServiceAwareConverter<RoleDbEntity, RoleResponse>{

	@Override
	public RoleResponse convert(RoleDbEntity source) {
		RoleResponse response = new RoleResponse(source.getRoleId());
		response.setPermission(source.getPermission());
		response.setName(source.getName());
		return response;
	}
}