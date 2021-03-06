package com.springjpa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
import org.springframework.web.multipart.MultipartFile;

import com.springjpa.annotation.MethodSessionValidationAnnotation;
import com.springjpa.aop.LogExecutionTime;
import com.springjpa.model.core.Customer;
import com.springjpa.model.core.GitHubUserInfo;
import com.springjpa.model.core.Order;
import com.springjpa.model.db.CustomerDbEntity;
import com.springjpa.model.db.RoleDbEntity;
import com.springjpa.model.db.AvatarDbEntity;
import com.springjpa.model.http.AddRoleRequest;
import com.springjpa.model.http.GetUsersBeanParams;
import com.springjpa.model.http.NotFoundException;
import com.springjpa.model.http.UploadFilesResponse;
import com.springjpa.repo.CustomerRepository;
import com.springjpa.repo.OrderRepository;
import com.springjpa.repo.RoleRepository;
import com.springjpa.service.AmazonS3Service;
import com.springjpa.service.CustomerService;
import com.springjpa.service.GitHubLookupService;

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
	
	@Autowired
	GitHubLookupService gitHubLookupService; 
	
	@Autowired
	private AmazonS3Service s3Service;
	
	
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
		
		return new ResponseEntity<>(customerEntity.getId(), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/users/{userId}", produces="application/json") 
	//if accept:application/json is provided as a header, this entry will be called; 
	//otherwise, the default method defined after this method will be called.  
	@MethodSessionValidationAnnotation
	@ResponseBody 
	@LogExecutionTime
	public ResponseEntity<Object> findById(@PathVariable("userId") String id) throws NotFoundException{
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
	
	@RequestMapping(value = "/users/{userId}") //default: can return xml if accept header not provided. 
	@MethodSessionValidationAnnotation
	public ResponseEntity<Object> findByIdDefault(@PathVariable("userId") String id){
		logger.info("findById: " + id);
		return new ResponseEntity<>(customerService.locateCustomer(id), HttpStatus.OK);
	}
	
	
	@RequestMapping("/users")
	@MethodSessionValidationAnnotation
	@LogExecutionTime
	public ResponseEntity<List<Customer>> fetchUsersByLastName(GetUsersBeanParams getUsersRequest){
		List<CustomerDbEntity> entityList = null;
		if(getUsersRequest.getIds() != null) {
			entityList = repository.findByIdIn(getUsersRequest.getIds());
		} else if(getUsersRequest.getLastName() != null) {
			entityList = repository.findByLastName(getUsersRequest.getLastName());
		} else if(getUsersRequest.getFirstName() != null) {
			entityList = repository.findByFirstName(getUsersRequest.getFirstName());
		} else {
			entityList = repository.findAll();
		}

		if(entityList.size()==0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
		List<Customer> customerList = new ArrayList<Customer>();
		List<CompletableFuture<GitHubUserInfo>> futureList = new ArrayList<CompletableFuture<GitHubUserInfo>>();
		
		logger.info("Found {} customers", entityList.size());
		
		for(CustomerDbEntity cust: entityList){
			logger.info("Adding customer: " + cust.getFirstName() + cust.getLastName());
			Customer customer = converionService.convert(cust, Customer.class);
			customerList.add(customer);
			
			//Has to wait for all calls to finish before return to avoid that all GitHub user infos are retrived and returned. 
			if(getUsersRequest.isEnhancedWithGithub()) {
				try {
					futureList.add(gitHubLookupService.findUser(customer));
				} catch (InterruptedException e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		
		if(getUsersRequest.isEnhancedWithGithub()) {
			// Wait until all calls are all done
			CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()])).join();
		}

		return new ResponseEntity<>(customerList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/users/{userId}/orders", method = RequestMethod.POST, produces="application/json")
	@MethodSessionValidationAnnotation
	public ResponseEntity<Object> purchase(@PathVariable("userId") String userId, @RequestBody Order purchaseOrderRequest){
		Order order = customerService.makePurchase(userId, purchaseOrderRequest);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/users/{userId}/orders", method = RequestMethod.GET, produces="application/json")
	@MethodSessionValidationAnnotation
	public ResponseEntity<Object> getOrders(@PathVariable("userId") String userId){
		CustomerDbEntity customerEntity = repository.findById(userId);
		List<Order> orders = new ArrayList<Order>();
		customerEntity.getOrders().forEach((orderEntity)->{
			Order order = converionService.convert(orderEntity, Order.class);
			orders.add(order);
		});
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@RequestMapping(value="/users/{userId}/avatars", method = RequestMethod.POST)
	@MethodSessionValidationAnnotation
	//TO-DO: add field validation 
    public ResponseEntity<UploadFilesResponse> uploadFile(@PathVariable("userId") String userId, @RequestParam("files") MultipartFile[] files) {
		UploadFilesResponse filesResponse = new UploadFilesResponse();
		Set<AvatarDbEntity> avatarsDbEntities = new HashSet<AvatarDbEntity>();
		Arrays.stream(files).forEach(file->{
			String fileName = this.s3Service.uploadFile(file);
			avatarsDbEntities.add(new AvatarDbEntity(fileName));
	        filesResponse.getFileNames().add(fileName);
		});
		
		CustomerDbEntity customerEntity = repository.findOne(userId);
		customerEntity.setAvatars(avatarsDbEntities);
		repository.save(customerEntity);
		
		return new ResponseEntity<>(filesResponse, HttpStatus.OK); 
    }

	@RequestMapping(value="/users/{userId}/avatars/{avatarId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteFile(@PathVariable("userId") String userId, @PathVariable("avatarId") int avatarId) {
		CustomerDbEntity customerEntity = repository.findOne(userId);
	    
		String fileName = null;
		boolean deleted = false;
		
		for(AvatarDbEntity avatar:customerEntity.getAvatars()) {
			if(avatar.getId() == avatarId) {
				fileName = avatar.getFileName();
				customerEntity.getAvatars().remove(avatar);
				repository.save(customerEntity);
				deleted = this.s3Service.deleteFileFromS3Bucket(fileName);
				break;
			}
		}
		
        return  new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR); 
    }
    
	
	@RequestMapping("/login")
	public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
		return UUID.randomUUID().toString();
	}
}

