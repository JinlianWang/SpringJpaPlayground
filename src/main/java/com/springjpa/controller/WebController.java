package com.springjpa.controller;

import java.util.Arrays;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springjpa.annotation.MethodSessionValidationAnnotation;
import com.springjpa.model.db.CustomerDbEntity;
import com.springjpa.model.http.NotFoundException;
import com.springjpa.repo.CustomerRepository;
import com.springjpa.service.CustomerService;
import com.springjpa.service.S3Services;

@RestController
public class WebController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
			
	@Autowired
	CustomerRepository repository;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	S3Services s3Service;
	
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
	
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.s3Service.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String file) {
        return this.s3Service.deleteFile(file);
    }
}

