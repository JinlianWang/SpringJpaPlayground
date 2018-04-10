package com.springjpa.controller;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springjpa.annotation.MethodSessionValidationAnnotation;
import com.springjpa.model.db.CustomerDbEntity;
import com.springjpa.model.http.CustomerResponse;
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
		repository.save(new CustomerDbEntity("Jack", "Smith"));
		
		// save a list of Customers
		repository.save(Arrays.asList(new CustomerDbEntity("Adam", "Johnson"), new CustomerDbEntity("Kim", "Smith"),
										new CustomerDbEntity("David", "Williams"), new CustomerDbEntity("Peter", "Davis")));
		
		return "Done";
	}
	
	
	@RequestMapping("/findall")
	@MethodSessionValidationAnnotation
	public String findAll(){
		String result = "";
		
		for(CustomerDbEntity cust : repository.findAll()){
			result += cust.toString() + "<br>";
		}
		
		return result;
	}
	
	@RequestMapping(value = "/findbyid", produces="application/json")
	@MethodSessionValidationAnnotation
	public CustomerResponse findById(@RequestParam("id") String id){
		return customerService.locateCustomer(id);
	}
	
	@RequestMapping(value = "/findbyid") //default: can return xml if accept header not provided. 
	@MethodSessionValidationAnnotation
	public CustomerResponse findByIdDefault(@RequestParam("id") String id){
		return customerService.locateCustomer(id);
	}
	
	@RequestMapping("/findbylastname")
	@MethodSessionValidationAnnotation
	public String fetchDataByLastName(@RequestParam("lastname") String lastName){
		String result = "";
		
		for(CustomerDbEntity cust: repository.findByLastName(lastName)){
			result += cust.getFirstName() + "<br>"; 
		}
		
		return result;
	}
	
	@RequestMapping("/login")
	public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
		return UUID.randomUUID().toString();
	}
}

