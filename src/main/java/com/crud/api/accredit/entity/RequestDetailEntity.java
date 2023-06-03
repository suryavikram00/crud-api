/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.accredit.entity;

import com.crud.api.accredit.accenum.AccreditGroupLevelEnum;
import com.crud.api.accredit.accenum.RequestActionEnum;
import com.crud.api.accredit.accenum.RequestStatusEnum;
import com.crud.api.generic.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author NMSLAP570
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "acc_request_detail")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RequestDetailEntity extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Column(name = "request_id", nullable = false)
//    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id", insertable = false, updatable = true)
    private RequestEntity request;
    
    @ManyToOne
    @JoinColumn(name = "accredit_group_id", referencedColumnName = "id", insertable = false, updatable = true)
    private AccreditGroup accreditGroup;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatusEnum status;

    @Column(name = "approver_comment", nullable = false)
    private String approverComment;

}
