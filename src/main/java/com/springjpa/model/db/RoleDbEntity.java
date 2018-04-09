package com.springjpa.model.db;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "roles")
public class RoleDbEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7983141855304184538L;
	
	@Id
	@Column(name="role_id")
	private int roleId;
	
	@Column(name="nm")
	private String name;
	
	@Transient
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
	
	@Override
	public String toString() {
		return String.format("Role[id=%s, name='%s']",this.roleId, this.name);
	}
}
