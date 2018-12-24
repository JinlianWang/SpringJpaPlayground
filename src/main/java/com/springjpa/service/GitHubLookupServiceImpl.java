package com.springjpa.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.springjpa.model.core.Customer;
import com.springjpa.model.core.GitHubUserInfo;

@Service
public class GitHubLookupServiceImpl implements GitHubLookupService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private final RestTemplate restTemplate;
	
    public GitHubLookupServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    @Async
	public CompletableFuture<GitHubUserInfo> findUser(Customer customer) throws InterruptedException{
        String url = String.format("https://api.github.com/users/%s", customer.getFirstName() + customer.getLastName());
        logger.debug("Looking up GitHub profile at: " + url);
        GitHubUserInfo info = restTemplate.getForObject(url, GitHubUserInfo.class);
        if(info.getLogin() != null) {
        	logger.debug("GitHub Profile is found for user: " + customer.getFirstName() + customer.getLastName());
        	customer.setGitHubInfo(info);
        } else {
        	logger.debug("GitHub Profile is found for user: " + customer.getFirstName() + customer.getLastName());
        }
        
        // Artificial delay of 2s for demonstration purposes
        Thread.sleep(2000L);
        
        logger.debug("After artificially delay for:" + customer.getFirstName() + customer.getLastName());
        
        return CompletableFuture.completedFuture(info);
	}
}
