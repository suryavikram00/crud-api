/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.generic.controller;

import com.crud.api.generic.entity.BaseEntity;
import com.crud.api.model.CrudApiResponse;
import javax.websocket.server.PathParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author NMSLAP570
 */
public interface IGenericController<T extends BaseEntity> {

    ResponseEntity<CrudApiResponse<T>> findAll();

    /**
     *
     * @param id
     * @return
     */
    ResponseEntity<CrudApiResponse<T>> findById(@PathVariable Long id);

    ResponseEntity<CrudApiResponse<T>> findAllByPageable(
            Boolean isPaged,
            @SortDefault(sort = "priRole")
            @PageableDefault(size = 20) final Pageable pageable);
    
    ResponseEntity<CrudApiResponse<T>> findByFilter(T t);

}
