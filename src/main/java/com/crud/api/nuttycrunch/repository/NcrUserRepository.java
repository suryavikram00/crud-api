package com.crud.api.nuttycrunch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.crud.api.nuttycrunch.entity.NcrUserEntity;

@Repository
public interface NcrUserRepository extends CrudRepository<NcrUserEntity, Integer> {

	NcrUserEntity findByUsername(String userName);

}