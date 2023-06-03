package com.crud.api.service.LoginService;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.crud.api.nuttycrunch.model.NcrUserModel;
import com.crud.api.nuttycrunch.entity.NcrUserEntity;
import com.crud.api.nuttycrunch.repository.NcrUserRepository;
import com.crud.api.utility.JwtTokenUtils;
import com.crud.api.utility.UserPermissionUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService {

    @Autowired
    private NcrUserRepository userDao;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    public NcrUserEntity checkUser(String username) {
        NcrUserEntity ncrUserEntity = userDao.findByUsername(username);
        return ncrUserEntity;
    }

    public static String encodeStringToBase64(String input) {
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes());
        return new String(encodedBytes);
    }

    public String generateToken(NcrUserModel authenticationRequest) throws Exception {

        final NcrUserEntity userDetails = checkUser(authenticationRequest.getUserName());
        if (Objects.isNull(userDetails)) {
            log.info("UserDetails is not present");
            throw new RuntimeException(UserPermissionUtils.EMAIL_NOT_REGISTERED);
        }
        if (!encodeStringToBase64(authenticationRequest.getPassword()).equals(userDetails.getPassword())) {
            log.info("Password is incorrect");
            throw new RuntimeException(UserPermissionUtils.PASSWORD_INCORRECT);
        }

        NcrUserModel userDetailsModel = new NcrUserModel(userDetails);
        if (!this.isAuthorizedToAccessConfigPanel(userDetailsModel)) {
            log.info("user is not authorized ::{}", JwtTokenUtils.getLoggedInUserId());
            throw new RuntimeException(UserPermissionUtils.USER_UNAUTHORISED);
        }

        String token = jwtTokenUtils.createJWT(userDetailsModel);
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(userDetailsModel, null, new ArrayList<>()));
        return token;

    }

    public boolean isAuthorizedToAccessConfigPanel(NcrUserModel ncrUserModel) {
        try {
            JSONObject permissionsJson = new JSONObject(ncrUserModel.getPermissionDetails());
            if (Objects.isNull(permissionsJson)) {
                log.info("User has not the permission to access the panel");
                return false;
            }
            return permissionsJson.has("ncr_config_panel")
                    && permissionsJson.getJSONObject("ncr_config_panel").has("login")
                    && permissionsJson.getJSONObject("ncr_config_panel").getBoolean("login") ? Boolean.TRUE
                    : Boolean.FALSE;
        } catch (JSONException e) {
            log.error("JSONException" + e);
            return false;
        } catch (Exception e) {
            log.error("Exception :: " + e);
            return false;
        }
    }


}
