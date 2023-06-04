/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.accredit.service;

import com.crud.api.accredit.accenum.RequestStatusEnum;
import com.crud.api.accredit.entity.AccreditGroupEntity;
import com.crud.api.accredit.entity.RequestDetailEntity;
import com.crud.api.accredit.entity.RequestEntity;
import com.crud.api.accredit.repository.IAccreditGroupRepository;
import com.crud.api.accredit.repository.IRequestDetailRepository;
import com.crud.api.accredit.repository.IRequestRepository;
import com.crud.api.exception.CrudApiException;
import com.crud.api.generic.entity.BaseEntity;
import com.crud.api.generic.enums.TableNameEnum;
import com.crud.api.nuttycrunch.service.*;
import com.crud.api.nuttycrunch.entity.FcMasterEntity;
import com.crud.api.generic.service.GenericService;
import com.crud.api.generic.service.IGenericService;
import com.crud.api.generic.service.utils.GenericUtility;
import com.crud.api.nuttycrunch.entity.SupplierMasterEntity;
import com.crud.api.utility.JwtTokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author NMSLAP570
 */
@Service
@Slf4j
public class RequestDetailService extends GenericService<RequestDetailEntity>
        implements IRequestDetailService {

    @Autowired
    private IRequestRepository requestRepository;
    @Autowired
    private IAccreditGroupRepository accreditGroupRepository;
    @Autowired
    private IRequestDetailRepository requestDetailRepository;
    @Autowired
    private IGenericService<BaseEntity> genericService;

    @Override
    @Transactional
    public RequestDetailEntity updateEntity(RequestDetailEntity requestDetailEntity) {

        if (!JwtTokenUtils.getLoggedInUserEmail().equalsIgnoreCase(requestDetailEntity.getApproverEmail())) {
            throw new CrudApiException("You cannot update the request, as you are not the approver");
        }

        if (!RequestStatusEnum.PENDING.equals(requestDetailEntity.getRequestStatus())) {
            throw new CrudApiException("Only pending state records can be updated | current status :: " + requestDetailEntity.getRequestStatus());
        }

        // if the status is rejected or approved then update it in the db
        requestDetailRepository.save(requestDetailEntity);

        // check the next level using the current level
        AccreditGroupEntity accreditGroupEntity = new AccreditGroupEntity();
        accreditGroupEntity.setTag(requestDetailEntity.getTag());
        accreditGroupEntity.setLevel(requestDetailEntity.getAccreditGroupLevel());

        List<AccreditGroupEntity> accreditGroupEntityList = accreditGroupRepository.findAll(Example.of(accreditGroupEntity, ExampleMatcher.matchingAll()), Pageable.unpaged()).getContent();

        Optional<RequestEntity> optional = requestRepository.findById(requestDetailEntity.getRequestId());
        RequestEntity requestEntity = optional.isPresent() ? optional.get() : null;
        if (requestEntity == null) {
            throw new CrudApiException("The request detail Id :: " + requestDetailEntity.getId() + " doesn't have a request mapped!");
        }

        if (RequestStatusEnum.REJECTED.equals(requestDetailEntity.getStatus())
                || RequestStatusEnum.APPROVED.equals(requestDetailEntity.getStatus())
                && accreditGroupEntityList.get(0).getNextLevel() == null) {
            // if the status is approved,
            // if it didnot require a another round of approval then update the request status as success
            requestEntity.setStatus(requestDetailEntity.getStatus());
            requestRepository.save(requestEntity);
            try {
                // we need to update the acutal entity using the existing and the new values
                String newValue = requestEntity.getNewValue();
                Class<?> clazz = GenericUtility.getClassName(TableNameEnum.NCR_SUPPLIER_LOGIN_DETAILS);
                ObjectMapper objectMapper = GenericUtility.getObjectMapper();
                BaseEntity baseEntity = (BaseEntity) objectMapper.readValue(newValue, clazz);
                genericService.updateEntity(baseEntity);
            } catch (JsonProcessingException ex) {
                log.error("Error when updating the values to the table :: "
                        + requestEntity.getTag() + " | identifier :: " + requestEntity.getUniqueIdentifier(), ex);
                throw new CrudApiException("Error when updating the values to the table :: "
                        + requestEntity.getTag() + " | identifier :: " + requestEntity.getUniqueIdentifier());
            }

        } else if (RequestStatusEnum.APPROVED.equals(requestDetailEntity.getStatus())
                && (accreditGroupEntityList.get(0).getNextLevel() != null)) {
            // if the status is approved and if required another round of approval        
            // if yes then insert the request details into the table            
            AccreditGroupEntity nextLevelaccreditGroupEntity = new AccreditGroupEntity();
            nextLevelaccreditGroupEntity.setLevel(accreditGroupEntityList.get(0).getNextLevel());
            List<AccreditGroupEntity> nextLevelaccreditGroupEntityList = accreditGroupRepository.findAll(Example.of(nextLevelaccreditGroupEntity, ExampleMatcher.matchingAll()), Pageable.unpaged()).getContent();
            requestDetailRepository.save(new RequestDetailEntity(requestEntity,
                    nextLevelaccreditGroupEntityList.get(0)));
        }
        return requestDetailEntity;
    }

}
