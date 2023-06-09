/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.nuttycrunch.entity;

import com.crud.api.generic.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author NMSLAP570
 */
@Table
@Entity(name = "ncr_supplier_login_details")
@Data
@NoArgsConstructor
public class SupplierLoginDetailsEntity extends BaseEntity<Long> implements Serializable {

    public static final long serialVersionUID = 2L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_code")
    private String supplierCode;
    
    @Column(name = "fc_code")
    @NotEmpty
    private String fcCode;
    
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "access_token")
    @NotEmpty
    private String accessToken;
    
    @Column(name = "external_customer_code")
    private String externalCustomerCode;
    
}
