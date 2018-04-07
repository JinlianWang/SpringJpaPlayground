package com.springjpa.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7983141855304184538L;
	
	@Id
	@Column(name="role_id")
	private String roleId;
	
	@Column(name="nm")
	private String name;
	
	@ManyToMany(mappedBy="roles")
	private Set<Customer> users;

	public String getRoleId() {
		return roleId;
	}

	public Set<Customer> getUsers() {
		return users;
	}

	public void setUsers(Set<Customer> users) {
		this.users = users;
	}

	public void setRoleId(String roleId) {
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
