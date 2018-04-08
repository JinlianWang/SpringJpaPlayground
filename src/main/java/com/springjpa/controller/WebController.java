package com.springjpa.controller;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springjpa.annotation.MethodSessionValidationAnnotation;
import com.springjpa.model.Customer;
import com.springjpa.repo.CustomerRepository;
import com.springjpa.service.CustomerService;

@RestController
public class WebController {

	@Autowired
	CustomerRepository repository;
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping("/save")
	@MethodSessionValidationAnnotation
	public String process(){
		// save a single Customer
		repository.save(new Customer("Jack", "Smith"));
		
		// save a list of Customers
		repository.save(Arrays.asList(new Customer("Adam", "Johnson"), new Customer("Kim", "Smith"),
										new Customer("David", "Williams"), new Customer("Peter", "Davis")));
		
		return "Done";
	}
	
	
	@RequestMapping("/findall")
	@MethodSessionValidationAnnotation
	public String findAll(){
		String result = "";
		
		for(Customer cust : repository.findAll()){
			result += cust.toString() + "<br>";
		}
		
		return result;
	}
	
	@RequestMapping("/findbyid")
	@MethodSessionValidationAnnotation
	public Customer findById(@RequestParam("id") String id){
		return customerService.locateCustomer(id);
	}
	
	@RequestMapping("/findbylastname")
	@MethodSessionValidationAnnotation
	public String fetchDataByLastName(@RequestParam("lastname") String lastName){
		String result = "";
		
		for(Customer cust: repository.findByLastName(lastName)){
			result += cust.getFirstName() + "<br>"; 
		}
		
		return result;
	}
	
	@RequestMapping("/login")
	public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
		return UUID.randomUUID().toString();
	}
}

