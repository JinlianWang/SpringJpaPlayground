package com.springjpa.model.db;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "users")
@NamedEntityGraphs({
	@NamedEntityGraph(name = "customer.Roles", attributeNodes = {@NamedAttributeNode(value = "roles")}),
	@NamedEntityGraph(name = "customer.Orders", attributeNodes = {@NamedAttributeNode(value = "orders")})
})
public class CustomerDbEntity implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@Column(name = "sso_id")
	private String id;

	@Column(name = "frst_nm")
	private String firstName;

	@Column(name = "last_nm")
	private String lastName;

	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "sso_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleDbEntity> roles;
	
	@OneToMany(mappedBy="customer", fetch=FetchType.LAZY)
	private Set<OrderDbEntity> orders;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="user_id")
	private Set<AvatarDbEntity> avatars;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	public Set<OrderDbEntity> getOrders() {
		return orders;
	}

	public void setOrders(Set<OrderDbEntity> orders) {
		this.orders = orders;
	}

	
	public Set<AvatarDbEntity> getAvatars() {
		return avatars;
	}

	public void setAvatars(Set<AvatarDbEntity> avatars) {
		this.avatars = avatars;
	}

	protected CustomerDbEntity() {
	}

	public CustomerDbEntity(String sso, String firstName, String lastName) {
		this.id = sso;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s', roles='%s']", id, firstName, lastName, this.getRoles().toString());
	}
}
