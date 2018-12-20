package com.springjpa.model.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="role")
@XmlAccessorType(XmlAccessType.FIELD)
public class Role {

	@XmlElement
	private int roleId;
	
	@XmlElement
	private String name;

	@XmlElement
	@XmlElementWrapper(name="permissions")
	private List<String> permission;
	
	public List<String> getPermission() {
		return permission;
	}

	public void setPermission(List<String> permission) {
		this.permission = permission;
	}

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
	
	public Role() {
		super();
	}
	
	public Role(int roleId) {
		super();
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return String.format("Role[id=%s, name='%s']",this.roleId, this.name);
	}
}
