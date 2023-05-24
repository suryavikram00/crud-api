package com.crud.api.controller;

import static com.crud.api.constants.Endpoints.ENDPOINT_ZIPCODE_DETAIL;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.api.entity.ZipcodeEntity;
import com.crud.api.generic.controller.GenericController;

@RestController
@RequestMapping(path = ENDPOINT_ZIPCODE_DETAIL)
public class ZipcodeController extends GenericController<ZipcodeEntity> implements IZipcodeController {

}
