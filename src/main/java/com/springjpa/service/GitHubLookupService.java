package com.springjpa.service;

import java.util.concurrent.CompletableFuture;

import com.springjpa.model.core.Customer;
import com.springjpa.model.core.GitHubUserInfo;

public interface GitHubLookupService {
	public CompletableFuture<GitHubUserInfo> findUser(Customer customer) throws InterruptedException;
}
