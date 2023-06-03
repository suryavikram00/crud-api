package com.crud.api.nuttycrunch.entity;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.crud.api.nuttycrunch.model.NcrConfigModel;
import com.crud.api.utility.JwtTokenUtils;
import com.ncr.config.enums.DataType;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Where(clause = "protected= false AND config_active= true")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ncr_config")
public class NcrConfigEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(name = "config_active", nullable = false)
    private Boolean active;

    @Column(name = "config_name", nullable = false, length = 128)
    private String configName;

    @Column(name = "config_value", length = 8196)
    private String configValue;

    @Column(name = "created_by", nullable = false)
    private int createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false)
    private Date createdOn;

    @Column(length = 4096)
    private String description;

    @Column(name = "protected", nullable = false)
    private Boolean protectedProd;

    @Column(name = "updated_by", nullable = false)
    private int updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on", nullable = false)
    private Date updatedOn;

    @Column(name = "write_enabled", nullable = false)
    private Boolean writeEnabled;

    @Column(name = "data_type", nullable = true)
    @Enumerated(EnumType.STRING)
    private DataType dataType;

    @Transient
    private String message;

    public NcrConfigEntity(NcrConfigModel model) {
        this.id = model.getId();
        this.configName = model.getConfigName();
        this.configValue = model.getConfigValue();
        this.description = model.getDescription();
        this.writeEnabled = model.getWriteEnabled();
        this.active = model.getActive();
        this.createdBy = model.getCreatedBy();
        this.createdOn = model.getCreatedOn();
        this.updatedBy = JwtTokenUtils.getLoggedInUserId();
        this.updatedOn = Date.from(Instant.now());
        this.protectedProd = model.getProtectedProd();
        this.dataType = model.getDataType();
        this.message = model.getMessage();
    }

    public void setDefaultValues() {
        this.writeEnabled = Boolean.TRUE;
        this.active = Boolean.TRUE;
        this.createdBy = Objects.nonNull(JwtTokenUtils.getLoggedInUserId()) ? JwtTokenUtils.getLoggedInUserId() : null;
        this.createdOn = new Date();
        this.updatedBy = Objects.nonNull(JwtTokenUtils.getLoggedInUserId()) ? JwtTokenUtils.getLoggedInUserId() : null;
        this.updatedOn = new Date();
        this.protectedProd = Boolean.FALSE;
        this.dataType = DataType.INT;        
    }
}
