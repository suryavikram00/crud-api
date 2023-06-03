package com.crud.api.nuttycrunch.controller;

import static com.crud.api.constants.Endpoints.ENDPOINT_ZIPCODE_DETAIL;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.api.nuttycrunch.entity.ZipcodeEntity;
import com.crud.api.generic.controller.GenericController;

@RestController
@RequestMapping(path = ENDPOINT_ZIPCODE_DETAIL)
public class ZipcodeController extends GenericController<ZipcodeEntity> implements IZipcodeController {

}
