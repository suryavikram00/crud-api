/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.generic.service;

import com.crud.api.generic.entity.BaseEntity;
import com.crud.api.generic.entity.SimplePage;
import com.crud.api.generic.repository.GenericRepository;
import com.crud.api.model.CrudApiResponse;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author NMSLAP570
 * @param <T>
 */
@Service
@Slf4j
public class GenericService<T extends BaseEntity> implements IGenericService<T> {

    @Autowired
    protected GenericRepository<T> genericRepository;

    @Override
    public List<T> findAll() throws Exception {
        try {
            return genericRepository.findAll();
        } catch (Exception e) {
            log.info("Exception in findAll method :: ", e);
            throw e;
        }
    }

    @Override
    public SimplePage<T> findByValue(T t) {
        log.info(" In Method :: {} {} ", t);
        List<T> ppoEntityList = new LinkedList<>();
        try {
            ExampleMatcher matcher = null;
            matcher = ExampleMatcher.matchingAny();
            ppoEntityList = genericRepository.findAll(Example.of(t, matcher));
            log.info(" Successfully fectched purchase order list of size :: {} ", ppoEntityList.size());
        } catch (Exception e) {
            log.info("Exception while fetching proposed po :: {} | {}" + t, e);
        }
        return new SimplePage<>(ppoEntityList, ppoEntityList.size(), Pageable.unpaged());
    }

    @Override
    public T findById(Long id) throws Exception {
        try {
            Optional<T> optional = genericRepository.findById(id);
            return optional.isPresent() ? optional.get() : null;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public SimplePage<T> findAll(final Pageable pageable) {
        final Page<T> page = genericRepository.findAll(pageable);
        return new SimplePage<>(page.getContent(), page.getTotalElements(), pageable);
    }

    @Override
    public T updateEntity(T t) {
        try {
            return genericRepository.save(t);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public T createEntity(T t) {
        try {
            return genericRepository.save(t);
        } catch (Exception e) {
            throw e;
        }
    }

}
