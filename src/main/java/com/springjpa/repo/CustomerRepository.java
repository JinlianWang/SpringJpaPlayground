package com.springjpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.model.db.CustomerDbEntity;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerDbEntity, String>, PermissionRetrieval {
	
	@EntityGraph(value = "roles", type = EntityGraph.EntityGraphType.LOAD)
	List<CustomerDbEntity> findByLastName(String lastName);
	
	List<CustomerDbEntity> findByFirstName(String firstName);
}
