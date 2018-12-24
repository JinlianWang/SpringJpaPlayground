package com.springjpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.model.db.CustomerDbEntity;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerDbEntity, String>, PermissionRetrieval {
	
	@EntityGraph(value = "customer.Roles", type = EntityGraph.EntityGraphType.LOAD)
	List<CustomerDbEntity> findByLastName(String lastName);
	
	List<CustomerDbEntity> findByFirstName(String firstName);
	
	//@Query("SELECT t FROM #{#entityName} t WHERE t.id IN :ids")//Does not seem to need this anymore? 
	List<CustomerDbEntity> findByIdIn(String[] ids);
	
	List<CustomerDbEntity> findAll();
	
	@EntityGraph(value = "customer.Orders", type = EntityGraph.EntityGraphType.LOAD)
	CustomerDbEntity findById(String Id);
}
