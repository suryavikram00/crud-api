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
import com.crud.api.generic.service.GenericService;
import java.util.List;
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
public class RequestService extends GenericService<RequestEntity>
        implements IRequestService {

    @Autowired
    private IRequestRepository requestRepository;
    @Autowired
    private IAccreditGroupRepository accreditGroupRepository;
    @Autowired
    private IRequestDetailRepository requestDetailRepository;

    @Override
    @Transactional
    public RequestEntity createEntity(RequestEntity requestEntity) {
        // if the record has a open request then reply back stating it has a open request
        RequestEntity pendingRequestEntity = new RequestEntity();
        pendingRequestEntity.setTag(requestEntity.getTag());
        pendingRequestEntity.setUniqueIdentifier(requestEntity.getUniqueIdentifier());
        pendingRequestEntity.setStatus(RequestStatusEnum.PENDING);
        List<RequestEntity> requestEntityList = super
                .findByValue(pendingRequestEntity, Pageable.unpaged(), Boolean.FALSE)
                .getContent();
        if (requestEntityList.size() > 0) {
            throw new CrudApiException("User cannot update this record, as requestId " + requestEntityList.get(0).getId() + "  is in pending state");
        }

        requestEntity.setInitialValues();

        AccreditGroupEntity accreditGroupEntity = new AccreditGroupEntity();
        accreditGroupEntity.setTag(requestEntity.getTag());
        // check whether the accredentials is present or not
        accreditGroupRepository.findAll(Example.of(accreditGroupEntity, ExampleMatcher.matchingAll()), Pageable.unpaged());
        List<AccreditGroupEntity> accreditGroupList = accreditGroupRepository.findAll(Example.of(accreditGroupEntity, ExampleMatcher.matchingAll()), Pageable.unpaged()).getContent();
        if (accreditGroupList.isEmpty()) {
            throw new CrudApiException("Accredition not found for the tag :: " + requestEntity.getTag());
        }

        requestEntity = super.createEntity(requestEntity);

        // if present, insert the request into the db in the db
        // use the accredentials and make an insert into the request details table
        RequestDetailEntity requestDetailEntity = new RequestDetailEntity(requestEntity,
                AccreditGroupEntity.getInitialAccredition(accreditGroupList));
        requestDetailRepository.save(requestDetailEntity);
        return requestRepository.save(requestEntity);
    }

}
