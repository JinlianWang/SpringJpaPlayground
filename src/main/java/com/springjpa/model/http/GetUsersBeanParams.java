package com.springjpa.model.http;

public class GetUsersBeanParams {

	private String firstName;
	
	private String lastName;
	
	private String[] Ids;

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

	public String[] getIds() {
		return Ids;
	}

	public void setIds(String[] ids) {
		Ids = ids;
	}
	
	
}
