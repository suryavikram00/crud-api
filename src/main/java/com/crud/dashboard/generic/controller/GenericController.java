/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.dashboard.generic.controller;

import com.crud.dashboard.generic.entity.BaseEntity;
import com.crud.dashboard.generic.service.IGenericService;
import javax.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author NMSLAP570
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@ResponseBody
@Slf4j
@RestController
@CrossOrigin("http://localhost:4200/")
public class GenericController<T extends BaseEntity> implements IGenericController<T> {

    @Autowired
    private IGenericService<T> genericService;

    @Override
    @GetMapping
    public ResponseEntity<T> findAll() {
        try {
            return new ResponseEntity(genericService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in findAll method :: ", e);
            return new ResponseEntity("Erro ao buscar todos!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/paginate")
    public ResponseEntity<T> findAllByPageable(
            Boolean isPaged,
            @SortDefault(sort = "id") @PageableDefault(size = 10) Pageable pageable) {
        try {
            if (isPaged == null || Boolean.FALSE.equals(isPaged)) {
                pageable = Pageable.unpaged();
            }
            return new ResponseEntity(genericService.findAll(pageable), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in findAll method :: ", e);
            return new ResponseEntity("Erro ao buscar todos!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<T> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity(genericService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in findAll method :: ", e);
            return new ResponseEntity("Erro ao buscar todos!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping(value = "/search")
    public ResponseEntity<T> findByFilter( T t) {        
        try {
            return new ResponseEntity(genericService.findByValue(t), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in findAll method :: ", e);
            return new ResponseEntity("Erro ao buscar todos!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
