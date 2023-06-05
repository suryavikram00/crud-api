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
import com.crud.api.generic.enums.StatusEnum;
import com.crud.api.generic.exception.CrudApiException;
import com.crud.api.model.CrudApiResponse;
import com.crud.api.utility.JwtTokenUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

//    @Override
//    @GetMapping("/paginate")
//    public ResponseEntity<CrudApiResponse<RequestDetailEntity>> findAllByPageable(
//            Boolean isPaged,
//            @SortDefault(sort = "id") @PageableDefault(size = 10) Pageable pageable) {
//        if (isPaged == null || Boolean.FALSE.equals(isPaged)) {
//            pageable = Pageable.unpaged();
//        }
//        RequestDetailEntity requestDetailEntity = new RequestDetailEntity();
//        requestDetailEntity.setApproverEmail(JwtTokenUtils.getLoggedInUserEmail());
//        RequestEntity requestEntity = new RequestEntity();
//        requestEntity.setStatus(RequestStatusEnum.PENDING);
//        requestDetailEntity.setRequest(requestEntity);
//        return super.findByFilter(requestDetailEntity, isPaged, pageable, Boolean.FALSE);
//    }

//    @Override
//    @GetMapping(value = "/search")
//    public ResponseEntity<CrudApiResponse<RequestDetailEntity>> findByFilter(RequestDetailEntity requestDetailEntity,
//            Boolean isPaged,
//            @SortDefault(sort = "id") @PageableDefault(size = 10) Pageable pageable,
//            @RequestParam(required=true,defaultValue="false") Boolean matchingAny) {
//        requestDetailEntity.setApproverEmail(JwtTokenUtils.getLoggedInUserEmail());
//        return super.findByFilter(requestDetailEntity, isPaged, pageable, matchingAny);
//    }

}
