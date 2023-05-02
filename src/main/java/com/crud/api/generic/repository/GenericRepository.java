/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.generic.repository;

/**
 *
 * @author NMSLAP570
 */
import com.crud.dashboard.generic.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GenericRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}
