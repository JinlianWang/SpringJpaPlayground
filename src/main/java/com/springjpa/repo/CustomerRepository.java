package com.springjpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String>{
	
	@EntityGraph(value = "roles", type = EntityGraph.EntityGraphType.LOAD)
	List<Customer> findByLastName(String lastName);
}
