package com.crud.api.controller;

import static com.crud.api.constants.Endpoints.ENDPOINT_V1_VERSION;
import java.util.Objects;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.api.model.NcrUserModel;
import com.crud.api.service.LoginService.JwtUserDetailsService;
import com.crud.api.utility.JwtTokenUtils;
import com.crud.api.utility.UserPermissionUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RestController
@RequestMapping(path = ENDPOINT_V1_VERSION)
@CrossOrigin(origins = "http://localhost:4200")
public class NcrUserController {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> createAuthenticationToken(@RequestBody NcrUserModel authenticationRequest)
            throws Exception {
        JSONObject jsonObject = new JSONObject();
        try {
            String token = userDetailsService.generateToken(authenticationRequest);
            if (Objects.isNull(token) || token.isEmpty()) {
                jsonObject.put("error", UserPermissionUtils.SOMETHING_WENT_WRONG);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonObject.toString());
            }
            jsonObject.put("jwttoken", token);
            return ResponseEntity.ok(jsonObject.toString());
        } catch (RuntimeException e) {
            log.info("runtime exception : {}", e.getMessage());
            jsonObject.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonObject.toString());
        } catch (Exception e) {
            log.info("exception caught: {}", e.getMessage());
            jsonObject.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonObject.toString());
        }

    }

}
