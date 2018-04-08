package com.springjpa.model.http;


import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerResponse {

	@XmlElement
	private String sso_id;

	@XmlElement
	private String firstName;

	@XmlElement
	private String lastName;

	@XmlElement
	private Set<RoleResponse> roles;

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

	public Set<RoleResponse> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleResponse> roles) {
		this.roles = roles;
	}
	
	protected CustomerResponse() {
	}

	public CustomerResponse(String sso_id) {
		this.sso_id = sso_id;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s', roles='%s']", sso_id, firstName, lastName, this.getRoles());
	}
}
