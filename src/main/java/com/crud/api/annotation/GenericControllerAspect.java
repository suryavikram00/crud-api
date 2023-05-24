/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.annotation;

import com.crud.api.generic.controller.GenericController;
import com.crud.api.generic.controller.IGenericController;
import com.crud.api.generic.entity.BaseEntity;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * @author NMSLAP570
 */
@Aspect
@Component
public class GenericControllerAspect {

    @Pointcut("within(@com.crud.api.annotation.CommonController)")
    public void inheritGenericControllerClass() {
    }

    @DeclareParents(value = "inheritGenericControllerClass()", defaultImpl = GenericController.class)
    private IGenericController<? extends BaseEntity> genericController;

}
