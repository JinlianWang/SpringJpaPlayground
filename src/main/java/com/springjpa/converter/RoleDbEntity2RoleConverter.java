package com.springjpa.converter;

import org.springframework.stereotype.Component;

import com.springjpa.model.core.Role;
import com.springjpa.model.db.RoleDbEntity;

@Component
public class RoleDbEntity2RoleConverter extends ConversionServiceAwareConverter<RoleDbEntity, Role>{

	@Override
	public Role convert(RoleDbEntity source) {
		Role response = new Role(source.getRoleId());
		response.setPermission(source.getPermission());
		response.setName(source.getName());
		return response;
	}
}