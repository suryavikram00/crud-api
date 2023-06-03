/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.accredit.entity;

import com.crud.api.accredit.accenum.RequestActionEnum;
import com.crud.api.accredit.accenum.RequestStatusEnum;
import com.crud.api.generic.entity.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;

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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.json.JSONException;
import org.json.JSONObject;

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
@Slf4j
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RequestEntity<T> extends BaseEntity<Long> {

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

    public void setTag(String tag) {
        this.tag = tag.toUpperCase();
    }

    public void setInitialValues() {
        this.status = RequestStatusEnum.PENDING;
        if (this.existingValue == null || this.existingValue.isEmpty()) {
            this.setAction(RequestActionEnum.CREATE);
        } else {
            this.setAction(RequestActionEnum.UPDATE);
        }
        this.submittedOn = new Date();
        this.submittedBy = "suryavikram@netmeds.com";
    }
//    // Getter and setter for newValue field
//    public JSONObject getNewValue() {
//        if (newValue == null) {
//            return null;
//        }
//        try {
//            return new JSONObject(newValue);
//        } catch (JSONException e) {
//            // Handle JSON parsing exception
//            log.info("Error in parsing json :: getNewValue :: " + e);
//            return null;
//        }
//    }
//
//    public void setNewValue(JSONObject newValue) {
//        if (newValue == null) {
//            this.newValue = null;
//        } else {
//            this.newValue = newValue.toString();
//        }
//    }
//
//// Getter and setter for existingValue field
//    public JSONObject getExistingValue() {
//        if (existingValue == null) {
//            return null;
//        }
//        try {
//            return new JSONObject(existingValue);
//        } catch (JSONException e) {
//            // Handle JSON parsing exception
//            log.info("Error in parsing json :: getExistingValue :: " + e);
//            return null;
//        }
//    }
//
//    public void setExistingValue(JSONObject existingValue) {
//        if (existingValue == null) {
//            this.existingValue = null;
//        } else {
//            this.existingValue = existingValue.toString();
//        }
//    }

}
