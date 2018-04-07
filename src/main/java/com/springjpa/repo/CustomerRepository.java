package com.springjpa.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String>{
	List<Customer> findByLastName(String lastName);
}
