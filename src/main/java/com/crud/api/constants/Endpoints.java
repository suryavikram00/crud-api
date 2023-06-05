package com.crud.api.constants;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Endpoints {

    public static final String ENDPOINT_V1_VERSION = "v1";
    public static final String ENDPOINT_V1_NMNCR_PREFIX = "/" + ENDPOINT_V1_VERSION;

    public static final String ENDPOINT_PPO = ENDPOINT_V1_NMNCR_PREFIX + "/ppo";
    public static final String ENDPOINT_PO = ENDPOINT_V1_NMNCR_PREFIX + "/po";
    public static final String ENDPOINT_SUPPLIER_LOGIN_DETAIL = ENDPOINT_V1_NMNCR_PREFIX + "/supplier-login-detail";
    public static final String ENDPOINT_SUPPLIER_MASTER = ENDPOINT_V1_NMNCR_PREFIX + "/supplier-master";
    public static final String ENDPOINT_FC_MASTER = ENDPOINT_V1_NMNCR_PREFIX + "/fc-master";
    public static final String ENDPOINT_ZIPCODE_DETAIL = ENDPOINT_V1_NMNCR_PREFIX + "/zipcode";
    public static final String ENDPOINT_ACC_REQUEST = ENDPOINT_V1_NMNCR_PREFIX + "/acc-request";
    public static final String ENDPOINT_ACC_ACCREDIT_GROUP = ENDPOINT_V1_NMNCR_PREFIX + "/acc-accredit-group";
    public static final String ENDPOINT_ACC_REQUEST_DETAIL = ENDPOINT_V1_NMNCR_PREFIX + "/acc-request-detail";

    public static final String GET_API_HEALTH_STATUS = "/status";
}
