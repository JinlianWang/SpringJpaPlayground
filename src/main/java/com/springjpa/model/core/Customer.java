package com.springjpa.model.core;


import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement(name="customer")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(Include.NON_NULL) //To exclude any value that is null
public class Customer {

	@XmlElement
	private String sso_id;

	@XmlElement
	private String firstName;

	@XmlElement
	private String lastName;

	@XmlElement
	@XmlElementWrapper(name="roles")
	private Set<Role> role;
	
	private GitHubUserInfo gitHubInfo;

	public GitHubUserInfo getGitHubInfo() {
		return gitHubInfo;
	}

	public void setGitHubInfo(GitHubUserInfo gitHubInfo) {
		this.gitHubInfo = gitHubInfo;
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

	public Set<Role> getRoles() {
		return role;
	}

	public void setRoles(Set<Role> roles) {
		this.role = roles;
	}
	
	protected Customer() {
	}

	public Customer(String sso_id) {
		this.sso_id = sso_id;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s', roles='%s']", sso_id, firstName, lastName, this.getRoles());
	}
}
