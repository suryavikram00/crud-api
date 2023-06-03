/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.nuttycrunch.repository;

import com.crud.api.generic.repository.GenericRepository;
import com.crud.api.nuttycrunch.entity.SupplierLoginDetailsEntity;
import com.crud.api.nuttycrunch.entity.SupplierMasterEntity;
import org.springframework.stereotype.Repository;

/**
 *
 * @author NMSLAP570
 */
@Repository
public interface ISupplierMasterRepository extends GenericRepository<SupplierMasterEntity> {
    
}
