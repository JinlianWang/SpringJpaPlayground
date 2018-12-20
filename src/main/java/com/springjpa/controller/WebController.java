package com.springjpa.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springjpa.annotation.MethodSessionValidationAnnotation;
import com.springjpa.model.core.Customer;
import com.springjpa.model.core.Order;
import com.springjpa.model.db.CustomerDbEntity;
import com.springjpa.model.db.OrderDbEntity;
import com.springjpa.model.db.RoleDbEntity;
import com.springjpa.model.http.AddRoleRequest;
import com.springjpa.model.http.NotFoundException;
import com.springjpa.model.http.PurchaseOrderRequest;
import com.springjpa.repo.CustomerRepository;
import com.springjpa.repo.OrderRepository;
import com.springjpa.repo.RoleRepository;
import com.springjpa.service.CustomerService;

@RestController
public class WebController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
			
	@Autowired
	CustomerRepository repository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ConversionService converionService;
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping("/initRoles")
	@MethodSessionValidationAnnotation
	public ResponseEntity<Object> initRoles(){
		// save multiple Customer
		List<RoleDbEntity> result = new ArrayList<RoleDbEntity>();
		RoleDbEntity adminRole = new RoleDbEntity(1, "admin");
		result.add(adminRole);
		RoleDbEntity userRole = new RoleDbEntity(2, "cardholder"); 
		result.add(userRole);
		
		roleRepository.save(result);
		return new ResponseEntity<>("Roles Initialized Successfully!", HttpStatus.OK);
	}
	
	@RequestMapping("/initUsers")
	@MethodSessionValidationAnnotation
	public ResponseEntity<Object> initUsers(){
		// save a single Customer
		CustomerDbEntity customerEntity = new CustomerDbEntity("2", "Jack", "Smith");
		RoleDbEntity roleEntity = roleRepository.findOne(2);
		customerEntity.setRoles(Collections.singleton(roleEntity));
		repository.save(customerEntity);
		
		customerEntity = new CustomerDbEntity("1", "Sunny", "Wang");
		repository.save(customerEntity);
		return new ResponseEntity<>("User Initialized Successfully!", HttpStatus.OK);
	}

	
	@RequestMapping(value="/users/{userId}/roles", method = RequestMethod.POST)
	@MethodSessionValidationAnnotation
	public ResponseEntity<Object> addRole(@PathVariable("userId") String userId, @RequestBody AddRoleRequest addRoleRequest){
		CustomerDbEntity customerEntity = repository.findOne(userId);
		RoleDbEntity roleEntity = roleRepository.findOne(addRoleRequest.getRoleId());
		customerEntity.getRoles().add(roleEntity);
		repository.save(customerEntity);
		
		return new ResponseEntity<>(customerEntity.getSso_id(), HttpStatus.CREATED);
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
	//if accept:application/json is provided as a header, this entry will be called; 
	//otherwise, the default method defined after this method will be called.  
	@MethodSessionValidationAnnotation
	@ResponseBody 
	public ResponseEntity<Object> findById(@RequestParam("id") String id) throws NotFoundException{
		logger.debug("Calling findById: " + id);
		if (StringUtils.isEmpty(id)) {
			logger.error("Error while calling findById: " + id);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			try {
				logger.info("FindById called successfully: " + id);
				return new ResponseEntity<>(customerService.locateCustomer(id), HttpStatus.OK);
			} catch (Exception e) {
				 logger.error("Error while calling findById: " + id);
				 throw new NotFoundException();
			}
		}
	}
	
	@RequestMapping(value = "/findbyid") //default: can return xml if accept header not provided. 
	@MethodSessionValidationAnnotation
	public ResponseEntity<Object> findByIdDefault(@RequestParam("id") String id){
		logger.info("findById: " + id);
		return new ResponseEntity<>(customerService.locateCustomer(id), HttpStatus.OK);
	}
	
	@RequestMapping("/findbylastname")
	@MethodSessionValidationAnnotation
	public ResponseEntity<List<Customer>> fetchDataByLastName(@RequestParam("lastname") String lastName){
		List<Customer> customerList = new ArrayList<Customer>();
		
		List<CustomerDbEntity> entityList = repository.findByLastName(lastName);
		
		if(entityList.size()==0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		logger.info("Found {} customers", entityList.size());
		
		for(CustomerDbEntity cust: entityList){
			logger.info("Adding customer name: " + cust.getFirstName());
			customerList.add(converionService.convert(cust, Customer.class));
		}
		
		return new ResponseEntity<>(customerList, HttpStatus.OK);
	}
	
	@RequestMapping("/findbyfirstname")
	@MethodSessionValidationAnnotation
	public ResponseEntity<List<Customer>> fetchDataByFirstName(@RequestParam("firstname") String firstName){
		List<Customer> customerList = new ArrayList<Customer>();
		
		List<CustomerDbEntity> entityList = repository.findByFirstName(firstName);
		
		if(entityList.size()==0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		logger.info("Found {} customers", entityList.size());
		
		for(CustomerDbEntity cust: entityList){
			logger.info("Adding customer name: " + cust.getFirstName());
			customerList.add(converionService.convert(cust, Customer.class));
		}
		
		return new ResponseEntity<>(customerList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/users/{userId}/orders", method = RequestMethod.POST, produces="application/json")
	@MethodSessionValidationAnnotation
	public ResponseEntity<Object> purchase(@PathVariable("userId") String userId, @RequestBody Order purchaseOrderRequest){
		Order order = customerService.makePurchase(userId, purchaseOrderRequest);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}
	
	
	@RequestMapping("/login")
	public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
		return UUID.randomUUID().toString();
	}
}

