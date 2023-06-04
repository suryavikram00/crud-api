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
import org.springframework.transaction.annotation.Transactional;

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
    public List<T> findAll() {
        return genericRepository.findAll();
    }

    @Override
    public SimplePage<T> findByValue(T t, Pageable pageable, Boolean matchingAny) {
        log.info(" In Method :: {} {} ", t);
        ExampleMatcher matcher = null;
        if (matchingAny) {
            matcher = ExampleMatcher.matchingAny();
        } else {
            matcher = ExampleMatcher.matchingAll();
        }

        final Page<T> page = genericRepository.findAll(Example.of(t, matcher), pageable);
        log.info(" Successfully fectched purchase order list of size :: {} ", page.getNumberOfElements());
        return new SimplePage<>(page.getContent(), page.getTotalElements(), pageable);

    }

    @Override
    public T findById(Long id) {
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
    @Transactional
    public T updateEntity(T t) {
        // if accredition is not enabled then, save

        // if enabled create a request
        return genericRepository.save(t);
    }

    @Override
    @Transactional
    public T createEntity(T t) {
        return genericRepository.save(t);
    }

    public void test() {
        log.info("In generic service test method");
    }

}
