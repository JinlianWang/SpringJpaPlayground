package com.springjpa.model.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="role")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleResponse {

	@XmlElement
	private int roleId;
	
	@XmlElement
	private String name;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public RoleResponse() {
		super();
	}
	
	public RoleResponse(int roleId) {
		super();
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return String.format("Role[id=%s, name='%s']",this.roleId, this.name);
	}
}
