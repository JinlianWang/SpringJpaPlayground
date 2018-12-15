package com.springjpa.repo;

import org.springframework.data.repository.CrudRepository;

import com.springjpa.model.db.RoleDbEntity;

public interface RoleRepository extends CrudRepository<RoleDbEntity, Integer> {

}
