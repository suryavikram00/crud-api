/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.nuttycrunch.controller;

import com.crud.api.generic.controller.GenericController;
import com.crud.api.nuttycrunch.entity.SupplierLoginDetailsEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.crud.api.constants.Endpoints.ENDPOINT_SUPPLIER_LOGIN_DETAIL;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author NMSLAP570
 */
@RestController
@RequestMapping(path = ENDPOINT_SUPPLIER_LOGIN_DETAIL)
@CrossOrigin(origins = "http://localhost:4200")
public class SupplierLoginDetailController extends GenericController<SupplierLoginDetailsEntity> 
        implements ISupplierLoginDetailController{
    
}
