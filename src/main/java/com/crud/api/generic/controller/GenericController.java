/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.generic.controller;

import com.crud.api.constants.StatusEnum;
import com.crud.api.generic.entity.BaseEntity;
import com.crud.api.generic.entity.SimplePage;
import com.crud.api.generic.service.IGenericService;
import com.crud.api.model.CrudApiResponse;
import java.util.List;
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
    public ResponseEntity<CrudApiResponse<T>> findAll() {
        try {
            List<T> list = genericService.findAll();
            CrudApiResponse<T> crudApiResponse = new CrudApiResponse<>(StatusEnum.SUCCESS);
            crudApiResponse.setObjectList(list);
            return new ResponseEntity(crudApiResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in findAll method :: ", e);
            return new ResponseEntity(new CrudApiResponse<T>(StatusEnum.FAILURE), HttpStatus.OK);
        }
    }

    @Override
    @GetMapping("/paginate")
    public ResponseEntity<CrudApiResponse<T>> findAllByPageable(
            Boolean isPaged,
            @SortDefault(sort = "id") @PageableDefault(size = 10) Pageable pageable) {
        try {
            if (isPaged == null || Boolean.FALSE.equals(isPaged)) {
                pageable = Pageable.unpaged();
            }
            SimplePage<T> page = genericService.findAll(pageable);
            CrudApiResponse<T> crudApiResponse = new CrudApiResponse<>(StatusEnum.SUCCESS);
            crudApiResponse.setPageData(page);
            return new ResponseEntity(crudApiResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in findAll method :: ", e);
            return new ResponseEntity(new CrudApiResponse<T>(StatusEnum.FAILURE), HttpStatus.OK);
        }
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<CrudApiResponse<T>> findById(@PathVariable Long id) {
        try {
            CrudApiResponse<T> crudApiResponse = new CrudApiResponse<>(StatusEnum.SUCCESS);
            crudApiResponse.setObject(genericService.findById(id));
            return new ResponseEntity(crudApiResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in findById method :: ", e);
            return new ResponseEntity(new CrudApiResponse<T>(StatusEnum.FAILURE), HttpStatus.OK);
        }
    }

    @Override
    @GetMapping(value = "/search")
    public ResponseEntity<CrudApiResponse<T>> findByFilter(T t) {
        try {
            SimplePage<T> page = genericService.findByValue(t);
            CrudApiResponse<T> crudApiResponse = new CrudApiResponse<>(StatusEnum.SUCCESS);
            crudApiResponse.setPageData(page);
            return new ResponseEntity(crudApiResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in findByFilter method :: ", e);
            return new ResponseEntity(new CrudApiResponse<T>(StatusEnum.FAILURE), HttpStatus.OK);
        }
    }
}
