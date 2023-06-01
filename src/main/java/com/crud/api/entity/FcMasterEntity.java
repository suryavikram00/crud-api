/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.entity;

import com.crud.api.generic.entity.BaseEntity;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "ncr_fc_master")
public class FcMasterEntity extends BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Integer id;

    @Column(name = "code")
    @NotBlank
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "mapped_wh")
    @NotBlank
    private String mappedWh;

    @NotNull
    @Column(name = "active")
    private Boolean active;

    @JsonIgnore
    @Column(name = "is_central_wh", nullable = false)
    private Boolean isCentralWh;

    @NotNull
    @Column(name = "auto_demand", nullable = false)
    private Boolean autoDemand;
    

    @Column(name = "entity_type", nullable = false)
    @JsonIgnore
    private String entityType;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FcMasterEntity other = (FcMasterEntity) obj;
        return Objects.equals(code, other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
