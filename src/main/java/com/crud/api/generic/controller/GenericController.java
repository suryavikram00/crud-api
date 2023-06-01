/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.generic.controller;

import com.crud.api.constants.StatusEnum;
import com.crud.api.generic.entity.BaseEntity;
import com.crud.api.generic.entity.SimplePage;
import com.crud.api.generic.service.IGenericService;
import com.crud.api.generic.service.utils.GenericUtility;
import com.crud.api.model.CrudApiResponse;
import com.crud.api.model.NcrUserModel;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PostMapping("/export")
    public void exportData(
            @RequestBody List<T> list,
            HttpServletResponse response) {
        try {
            Class<T> genericType = ((Class) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0]);
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + genericType.getSimpleName() + ".csv\"");
            PrintWriter writer = response.getWriter();
            writer.println(GenericUtility.getFieldNames(genericType));
            for (T eachObject : list) {
                writer.println(GenericUtility.extractFieldValues(eachObject));
            }
        } catch (Exception e) {
            log.error("Exception in findAll method :: ", e);
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

    @Override
    @PutMapping
    public ResponseEntity<CrudApiResponse<T>> updateEntity(@Valid @RequestBody T t) {
        try {
            CrudApiResponse<T> crudApiResponse = new CrudApiResponse<>(StatusEnum.SUCCESS);
            crudApiResponse.setObject(genericService.updateEntity(t));
            return new ResponseEntity(crudApiResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in findByFilter method :: ", e);
            return new ResponseEntity(new CrudApiResponse<T>(StatusEnum.FAILURE), HttpStatus.OK);
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<CrudApiResponse<T>> createEntity(@Valid @RequestBody T t) {
        try {
            CrudApiResponse<T> crudApiResponse = new CrudApiResponse<>(StatusEnum.SUCCESS);
            crudApiResponse.setObject(genericService.updateEntity(t));
            return new ResponseEntity(crudApiResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in findByFilter method :: ", e);
            return new ResponseEntity(new CrudApiResponse<T>(StatusEnum.FAILURE), HttpStatus.OK);
        }
    }
}
