/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.accredit.controller;

import com.crud.api.accredit.accenum.RequestActionEnum;
import com.crud.api.accredit.accenum.RequestStatusEnum;
import com.crud.api.accredit.entity.AccreditGroupEntity;
import com.crud.api.accredit.entity.RequestDetailEntity;
import com.crud.api.accredit.entity.RequestEntity;
import com.crud.api.accredit.service.IAccreditGroupService;
import com.crud.api.accredit.service.IRequestDetailService;
import com.crud.api.accredit.service.IRequestService;
import com.crud.api.generic.controller.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.crud.api.constants.Endpoints.ENDPOINT_ACC_REQUEST;
import com.crud.api.constants.StatusEnum;
import com.crud.api.exception.CrudApiException;
import com.crud.api.generic.service.IGenericService;
import com.crud.api.model.CrudApiResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author NMSLAP570
 */
@RestController
@RequestMapping(path = ENDPOINT_ACC_REQUEST)
@CrossOrigin(origins = "http://localhost:4200")
public class RequestController extends GenericController<RequestEntity>
        implements IRequestController {

    @Autowired
    private IRequestService requestService;

    @Autowired
    private IAccreditGroupService accreditGroupService;

    @Autowired
    private IRequestDetailService requestDetailService;

    @Override
    @PostMapping
    public ResponseEntity<CrudApiResponse<RequestEntity>> createEntity(@Valid @RequestBody RequestEntity requestEntity) {

        // if the record has a open request then reply back stating it has a open request
        RequestEntity pendingRequestEntity = new RequestEntity();
        pendingRequestEntity.setTag(requestEntity.getTag());
        pendingRequestEntity.setUniqueIdentifier(requestEntity.getUniqueIdentifier());
        pendingRequestEntity.setStatus(RequestStatusEnum.PENDING);
        List<RequestEntity> requestEntityList = requestService
                .findByValue(pendingRequestEntity, Pageable.unpaged(), Boolean.FALSE)
                .getContent();
        if (requestEntityList.size() > 0) {
            throw new CrudApiException("User cannot update this record, as requestId " + requestEntityList.get(0).getId() + "  is in pending state");
        }

        requestEntity.setInitialValues();

        AccreditGroupEntity accreditGroupEntity = new AccreditGroupEntity();
        accreditGroupEntity.setTag(requestEntity.getTag());
        // check whether the accredentials is present or not
        List<AccreditGroupEntity> accreditGroupList = accreditGroupService.findByValue(accreditGroupEntity, Pageable.unpaged(), Boolean.TRUE).getContent();
        if (accreditGroupList.isEmpty()) {
            throw new CrudApiException("Accredition not found for the tag :: " + requestEntity.getTag());
        }

        requestEntity = requestService.createEntity(requestEntity);

        // if present, insert the request into the db in the db
        // use the accredentials and make an insert into the request details table
        RequestDetailEntity requestDetailEntity = new RequestDetailEntity(requestEntity,
                AccreditGroupEntity.getInitialAccredition(accreditGroupList));
        requestDetailService.createEntity(requestDetailEntity);

        CrudApiResponse<RequestEntity> crudApiResponse = new CrudApiResponse<RequestEntity>(StatusEnum.SUCCESS).addMessage("Successfully submitted for approval!");
        crudApiResponse.setObject(requestEntity);
        return new ResponseEntity(crudApiResponse, HttpStatus.OK);
    }

    @Override
    @PutMapping
    public ResponseEntity<CrudApiResponse<RequestEntity>> updateEntity(@Valid @RequestBody RequestEntity requestEntity) {
        // if cancellation, then check the existing status, if it pending then only it can be cancelled
        RequestEntity existingRequestEntity = requestService.findById(requestEntity.getId());
        if (!RequestStatusEnum.PENDING.equals(existingRequestEntity.getStatus())) {
            throw new CrudApiException("The request cannot be changed to " + requestEntity.getStatus() + " state, as It is in " + existingRequestEntity.getStatus().name() + " state");
        }

        // other than cancellation, we can reject the request
        if (!RequestStatusEnum.CANCELLED.equals(requestEntity.getStatus())) {
            throw new CrudApiException("Only " + RequestStatusEnum.CANCELLED.name() + " is allowed | provided input " + requestEntity.getStatus());
        }
        return super.updateEntity(requestEntity);
    }

}
