/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.accredit.controller;

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
import static com.crud.api.constants.Endpoints.ENDPOINT_ACC_REQUEST_DETAIL;
import com.crud.api.constants.StatusEnum;
import com.crud.api.exception.CrudApiException;
import com.crud.api.model.CrudApiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author NMSLAP570
 */
@RestController
@RequestMapping(path = ENDPOINT_ACC_REQUEST_DETAIL)
@CrossOrigin(origins = "http://localhost:4200")
public class RequestDetailController extends GenericController<RequestDetailEntity>
        implements IRequestDetailController {

    @Autowired
    private IRequestDetailService requestDetailService;

    @Autowired
    private IAccreditGroupService accreditGroupService;

    @Autowired
    private IRequestService requestService;

    @Override
    @PutMapping
    public ResponseEntity<CrudApiResponse<RequestDetailEntity>> updateEntity(@RequestBody final RequestDetailEntity requestDetailEntity) {
                                
        // if the status is rejected or approved then update it in the db
        requestDetailService.updateEntity(requestDetailEntity);

        if (!RequestStatusEnum.PENDING.equals(requestDetailEntity.getRequest().getStatus())) {
            throw new CrudApiException("Only pending state records can be updated | current status :: " + requestDetailEntity.getRequest().getStatus());
        }

        if (RequestStatusEnum.REJECTED.equals(requestDetailEntity.getStatus())
                || RequestStatusEnum.APPROVED.equals(requestDetailEntity.getStatus())
                && requestDetailEntity.getAccreditGroup().getNextLevel() == null) {
            // if the status is approved, 
            // if it didnot require a another round of approval then update the request status as success
            RequestEntity requestEntity = requestDetailEntity.getRequest();
            requestEntity.setStatus(requestDetailEntity.getStatus());
            requestService.updateEntity(requestEntity);
            
            // we need to update the acutal entity using the existing and the new values
            
        } else if (RequestStatusEnum.APPROVED.equals(requestDetailEntity.getStatus())
                && (requestDetailEntity.getAccreditGroup().getNextLevel() != null)) {
            // if the status is approved and if required another round of approval        
            // if yes then insert the request details into the table
            AccreditGroupEntity accreditGroupEntity = new AccreditGroupEntity();
            accreditGroupEntity.setTag(requestDetailEntity.getAccreditGroup().getTag());
            accreditGroupEntity.setLevel(requestDetailEntity.getAccreditGroup().getNextLevel());
            List<AccreditGroupEntity> accreditGroupEntityList = accreditGroupService
                    .findByValue(accreditGroupEntity, Pageable.unpaged(), Boolean.FALSE).getContent();
            requestDetailService.updateEntity(new RequestDetailEntity(requestDetailEntity.getRequest(),
                    accreditGroupEntityList.get(0)));
        }

        CrudApiResponse<RequestDetailEntity> crudApiResponse = new CrudApiResponse<RequestDetailEntity>(StatusEnum.SUCCESS)
                .addMessage("Successfully updated the status of the request!");
        crudApiResponse.setObject(requestDetailEntity);
        return new ResponseEntity(crudApiResponse, HttpStatus.OK);
    }

}
