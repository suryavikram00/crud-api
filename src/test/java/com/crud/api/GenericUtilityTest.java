/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api;

import com.crud.api.generic.entity.BaseEntity;
import com.crud.api.generic.enums.TableNameEnum;
import com.crud.api.generic.service.GenericService;
import com.crud.api.generic.service.IGenericService;
import com.crud.api.generic.service.utils.GenericUtility;
import com.crud.api.nuttycrunch.entity.SupplierLoginDetailsEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

/**
 *
 * @author NMSLAP570
 */
public class GenericUtilityTest {

    @Test
    public void test() {
        try {
            String newValue = "{}";
            Class<?> clazz = GenericUtility.getClassName(TableNameEnum.NCR_SUPPLIER_LOGIN_DETAILS);
            ObjectMapper objectMapper = GenericUtility.getObjectMapper();
            BaseEntity supplierLoginDetailsEntity = (BaseEntity) objectMapper.readValue(newValue, clazz);            
            IGenericService<BaseEntity> genericService = new GenericService<>();
            genericService.test();
        } catch (JsonProcessingException ex) {
            Logger.getLogger(GenericUtilityTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
