package com.springjpa.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubUserInfo {
	
	@JsonProperty("id")
	private String gitHubId; 
	
	@JsonProperty("public_repos")
	private int publicRepoNo;
	
	@JsonProperty("avatar_url")
	private String avatarUrl;
	
	//Without JsonProperty, it defaults to the same name.
	private String login; 
	
	@JsonProperty("html_url")
	private String htmlUrl;
	
	//Without JsonProperty, it defaults to the same name. 
	private String type;
	
	public String getGitHubId() {
		return gitHubId;
	}
	public void setGitHubId(String gitHubId) {
		this.gitHubId = gitHubId;
	}
	public int getPublicRepoNo() {
		return publicRepoNo;
	}
	public void setPublicRepoNo(int publicRepoNo) {
		this.publicRepoNo = publicRepoNo;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getHtmlUrl() {
		return htmlUrl;
	}
	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
