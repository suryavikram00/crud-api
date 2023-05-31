/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.generic.service;

import com.crud.api.generic.entity.BaseEntity;
import com.crud.api.generic.entity.SimplePage;
import com.crud.api.model.CrudApiResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author NMSLAP570
 */
public interface IGenericService<T extends BaseEntity> {

    List<T> findAll() throws Exception;

    public SimplePage<T>  findByValue(T t);

    public SimplePage<T> findAll(final Pageable pageable);

    T findById(Long id) throws Exception;
    
    T updateEntity(T t);

}
