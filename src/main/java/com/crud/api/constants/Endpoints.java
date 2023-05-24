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
    public static final String ENDPOINT_ZIPCODE_DETAIL = ENDPOINT_V1_NMNCR_PREFIX + "/zipcode";

    public static final String GET_API_HEALTH_STATUS = "/status";
}
