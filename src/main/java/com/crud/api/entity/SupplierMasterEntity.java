/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.entity;

import com.crud.api.generic.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author NMSLAP570
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "ncr_supplier_master")
public class SupplierMasterEntity extends BaseEntity<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "address_line1", nullable = false, length = 256)
    private String addressLine1;

    @Column(name = "address_line2", nullable = false, length = 256)
    private String addressLine2;

    @Column(name = "address_line3", nullable = false, length = 256)
    private String addressLine3;

    @Column(name = "auto_po", nullable = false)
    private Boolean autoPo;

    @Column(name = "auto_send_email", nullable = false)
    private Boolean autoSendEmail;

    @Column(name = "base_url", nullable = false, length = 512)
    private String baseUrl;

    @Column(length = 256)
    private String city;

    @Column(name = "contact_no", nullable = false, length = 45)
    private String contactNo;

    @Column(name = "contact_person", nullable = false, length = 128)
    private String contactPerson;

    @Column(name = "contact_person_email_id", nullable = false, length = 128)
    private String contactPersonEmailId;

    @Column(name = "default_expiry_in_days", nullable = false)
    private Integer defaultExpiryInDays;

    @Column(name = "discount_pt", nullable = false)
    private Float discountPt;

    @Column(name = "email_id", length = 128)
    private String emailId;

    @Column(name = "external_supplier_code", length = 45)
    private String externalSupplierCode;

    @Column(length = 256)
    private String fccode;

    @Column(name = "gst_provisional_id", length = 128)
    private String gstProvisionalId;

    @Column(name = "max_openpo_value", nullable = false, precision = 10, scale = 3)
    private BigDecimal maxOpenpoValue;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false, length = 16)
    private String pincode;

    @Column(name = "po_software", nullable = false, length = 128)
    private String poSoftware;

    @Column(nullable = false)
    private Integer priority;

    @Column(name = "state_name", nullable = false, length = 128)
    private String stateName;

    @Column(name = "supplier_code", nullable = false, length = 128)
    private String supplierCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false)
    private Date createdOn;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "updated_by", nullable = false)
    private Integer updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on", nullable = false)
    private Date updatedOn;

    @Column(name = "auto_email_otc_generatePo")
    private Boolean autoEmailOtcGeneratePo;

}
