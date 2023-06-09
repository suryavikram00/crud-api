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
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.Transient;
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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RequestDetailEntity extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private RequestEntity request;

    @Column(name = "request_id", nullable = false)
    private Long requestId;

//    @ManyToOne
//    @JoinColumn(name = "accredit_group_id", referencedColumnName = "id", insertable = false, updatable = false)
//    @JsonIgnore
//    private AccreditGroupEntity accreditGroup;
    @Column(name = "approver_email", nullable = false)
    private String approverEmail;

    @Column(name = "accredit_group_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccreditGroupLevelEnum accreditGroupLevel;

//    @Column(name = "accredit_group_id", nullable = false)
//    @JsonIgnore
//    private Long accreditGroupId;
    @Column(name = "approver_comment", nullable = false)
    private String approverComment;

    @Transient
    private String tag;

    @Transient
    private String uniqueIdentifier;

    @Transient
    private String newValue;

    @Transient
    private String existingValue;

    @Transient
    @Enumerated(EnumType.STRING)
    private RequestActionEnum action;

    @Transient
    @Enumerated(EnumType.STRING)
    private RequestStatusEnum requestStatus;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatusEnum status;

    public String getTag() {
        return this.request != null ? this.request.getTag() : this.tag;
    }

    public String getUniqueIdentifier() {
        return this.request != null ? this.request.getUniqueIdentifier() : this.uniqueIdentifier;
    }

    public RequestStatusEnum getRequestStatus() {
        return this.request != null ? this.request.getStatus() : this.requestStatus;
    }

    public String getNewValue() {
        return this.request != null ? this.request.getNewValue() : this.newValue;
    }

    public String getExistingValue() {
        return this.request != null ? this.request.getExistingValue() : this.existingValue;
    }

    public RequestActionEnum getAction() {
        return this.request != null ? this.request.getAction() : null;
    }

//    public AccreditGroupLevelEnum getLevel() {
//        return this.accreditGroup.getLevel();
//    }
//
//    public String getApproverEmail() {
//        return this.accreditGroup.getApproverEmail();
//    }
    public RequestDetailEntity(RequestEntity requestEntity, AccreditGroupEntity accreditGroupEntity) {
        this.request = requestEntity;
        this.requestId = requestEntity.getId();
        this.status = RequestStatusEnum.PENDING;
        this.tag = requestEntity.getTag();
        this.uniqueIdentifier = requestEntity.getUniqueIdentifier();
        this.newValue = requestEntity.getNewValue();
        this.existingValue = requestEntity.getExistingValue();
        this.accreditGroupLevel = accreditGroupEntity.getLevel();
        this.approverEmail = accreditGroupEntity.getApproverEmail();
    }

    public void setRequest(RequestEntity requestEntity) {
        this.request = requestEntity;
        this.tag = requestEntity.getTag();
        this.uniqueIdentifier = requestEntity.getUniqueIdentifier();
        this.newValue = requestEntity.getNewValue();
        this.existingValue = requestEntity.getExistingValue();
    }

}
