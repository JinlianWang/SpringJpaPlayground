package com.springjpa.model.http;

public class GetUsersBeanParams {

	private String firstName;
	
	private String lastName;
	
	private String[] Ids;
	
	private boolean enhancedWithGithub;

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

	public boolean isEnhancedWithGithub() {
		return enhancedWithGithub;
	}

	public void setEnhancedWithGithub(boolean enhancedWithGithub) {
		this.enhancedWithGithub = enhancedWithGithub;
	}
	
	@Override
	public String toString() {
		return String.format("GetUsersBeanParams[ids=%s, firstName='%s', lastName='%s']", Ids, firstName, lastName);
	}
}
