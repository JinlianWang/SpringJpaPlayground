package com.springjpa.repo;

import org.springframework.data.repository.CrudRepository;

import com.springjpa.model.db.OrderDbEntity;

public interface OrderRepository extends CrudRepository<OrderDbEntity, String> {

}
