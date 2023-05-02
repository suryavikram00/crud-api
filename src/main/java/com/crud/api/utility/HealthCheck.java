package com.crud.api.utility;

import static com.crud.dashboard.constants.Endpoints.ENDPOINT_V1_NMNCR_PREFIX;
import static com.crud.dashboard.constants.Endpoints.GET_API_HEALTH_STATUS;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ENDPOINT_V1_NMNCR_PREFIX)
public class HealthCheck {

	@GetMapping(GET_API_HEALTH_STATUS)
	public ResponseEntity<String> getHealthStatus() {
		return new ResponseEntity<>("Healthy", HttpStatus.OK);
	}

}
