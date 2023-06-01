/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.controller;

import com.crud.api.generic.controller.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.crud.api.constants.Endpoints.ENDPOINT_FC_MASTER;
import com.crud.api.entity.FcMasterEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author NMSLAP570
 */
@RestController
@RequestMapping(path = ENDPOINT_FC_MASTER)
@CrossOrigin(origins = "http://localhost:4200")
public class FcrMasterController extends GenericController<FcMasterEntity> 
        implements IFcMasterMasterController{
    
}
