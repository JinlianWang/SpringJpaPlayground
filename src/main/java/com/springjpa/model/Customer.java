package com.springjpa.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "users")
public class Customer implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	private String sso_id;

	@Column(name = "frst_nm")
	private String firstName;

	@Column(name = "last_nm")
	private String lastName;

	@ManyToMany
	@JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "sso_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	protected Customer() {
	}

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s', roles='%s']", sso_id, firstName, lastName, this.getRoles());
	}
}
