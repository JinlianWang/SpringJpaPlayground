package com.springjpa.model.db;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "users")
@NamedEntityGraph(name = "roles", attributeNodes = {@NamedAttributeNode(value = "roles")})
public class CustomerDbEntity implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	private String sso_id;

	@Column(name = "frst_nm")
	private String firstName;

	@Column(name = "last_nm")
	private String lastName;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "sso_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleDbEntity> roles;

	public String getSso_id() {
		return sso_id;
	}

	public void setSso_id(String sso_id) {
		this.sso_id = sso_id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<RoleDbEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDbEntity> roles) {
		this.roles = roles;
	}
	
	protected CustomerDbEntity() {
	}

	public CustomerDbEntity(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s', roles='%s']", sso_id, firstName, lastName, this.getRoles());
	}
}
