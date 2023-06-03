/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.accredit.entity;

import com.crud.api.accredit.accenum.RequestActionEnum;
import com.crud.api.accredit.accenum.RequestStatusEnum;
import com.crud.api.generic.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vladmihalcea.hibernate.type.json.JsonType;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.Where;

/**
 *
 * @author NMSLAP570
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "acc_request")
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonType.class) // Custom JsonUserType for mapping JSON to String column
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RequestEntity extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "unique_identifier", nullable = false)
    private String uniqueIdentifier;

    @Column(name = "new_value", nullable = false)
    @Type(type = "json")
    private String newValue;

    @Column(name = "existing_value")
    @Type(type = "json")
    private String existingValue;

    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestActionEnum action;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatusEnum status;

    @Column(name = "submittedBy", nullable = false)
    private String submittedBy;

    @Column(name = "submittedOn", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedOn;

    @OneToMany(mappedBy = "request")
    @Where(clause = "status = 'PENDING'")
    private List<RequestDetailEntity> requestDetailList;

}
