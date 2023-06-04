/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.accredit.controller;

import com.crud.api.accredit.accenum.RequestStatusEnum;
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
import com.crud.api.model.CrudApiResponse;
import com.crud.api.utility.JwtTokenUtils;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
    @PutMapping
    public ResponseEntity<CrudApiResponse<RequestEntity>> updateEntity(@Valid @RequestBody RequestEntity requestEntity) {

        if (!JwtTokenUtils.getLoggedInUserEmail().equalsIgnoreCase(requestEntity.getSubmittedBy())) {
            throw new CrudApiException("You cannot update the request, as you have not the owner of the request");
        }
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

    @Override
    @PostMapping
    public ResponseEntity<CrudApiResponse<RequestEntity>> createEntity(@Valid @RequestBody RequestEntity t) {
        return new ResponseEntity(super.createEntity(t)
                .getBody()
                .addMessage("Request has been submitted successfully!"),
                HttpStatus.OK);
    }

//    @Override
//    @GetMapping("/paginate")
//    public ResponseEntity<CrudApiResponse<RequestEntity>> findAllByPageable(
//            Boolean isPaged,
//            @SortDefault(sort = "id") @PageableDefault(size = 10) Pageable pageable) {
//        if (isPaged == null || Boolean.FALSE.equals(isPaged)) {
//            pageable = Pageable.unpaged();
//        }
//        RequestEntity requestEntity = new RequestEntity();
//        requestEntity.setSubmittedBy(JwtTokenUtils.getLoggedInUserEmail());
//        return super.findByFilter(requestEntity, isPaged, pageable, Boolean.FALSE);
//    }
//    @Override
//    @GetMapping(value = "/search")
//    public ResponseEntity<CrudApiResponse<RequestEntity>> findByFilter(RequestEntity requestEntity,
//            Boolean isPaged,
//            @SortDefault(sort = "id") @PageableDefault(size = 10) Pageable pageable,
//            @RequestParam(required=true,defaultValue="false") Boolean matchingAny) {
//        requestEntity.setSubmittedBy(JwtTokenUtils.getLoggedInUserEmail());
//        return super.findByFilter(requestEntity, isPaged, pageable, matchingAny);
//    }
}
