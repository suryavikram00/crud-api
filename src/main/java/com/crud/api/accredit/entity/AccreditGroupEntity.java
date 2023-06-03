/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.accredit.entity;

import com.crud.api.accredit.accenum.AccreditGroupLevelEnum;
import com.crud.api.generic.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "acc_accredit_group")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AccreditGroupEntity extends BaseEntity<Long> {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "level", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccreditGroupLevelEnum level;

    @Column(name = "approver_email", nullable = false)
    @NotNull
    private String approverEmail;

    @Column(name = "next_level")
    @Enumerated(EnumType.STRING)
    private AccreditGroupLevelEnum nextLevel;

    public static AccreditGroupEntity getInitialAccredition(List<AccreditGroupEntity> accreditGroupList) {
        for (AccreditGroupEntity eachAccreditGroupEntity : accreditGroupList) {
            if (AccreditGroupLevelEnum.L1.equals(eachAccreditGroupEntity.getLevel())) {
                return eachAccreditGroupEntity;
            }
        }
        return null;
    }

}
