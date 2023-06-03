package com.crud.api.utility;

import java.security.Key;
import java.util.Date;
import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.crud.api.nuttycrunch.model.NcrUserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JwtTokenUtils {

    private static final String API_KEY = "apikey"; // salt

    // 20 mts from the token is created
    private static final Long EXPIRATION_MILLI_SECOND = (long) 1800000;

    private static final String ISSUER = "netmeds.com"; // merchant_key

    static Integer userId = null;

    static String permissionDetails = null;

    public String createJWT(NcrUserModel ncrUserModel) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(API_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = null;
        builder = Jwts.builder().setId(ncrUserModel.getId().toString()).setIssuedAt(now)
                .setSubject(ncrUserModel.getUserName()).setIssuer(ISSUER)
                .claim("userPermission", ncrUserModel.getPermissionDetails()).signWith(signatureAlgorithm, signingKey);

        if (EXPIRATION_MILLI_SECOND >= 0) {
            long expMillis = nowMillis + EXPIRATION_MILLI_SECOND;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    @Deprecated
    public NcrUserModel getUser() {
        NcrUserModel ncrUserModel = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof NcrUserModel) {
            ncrUserModel = ((NcrUserModel) principal);
        }
        return ncrUserModel;
    }

    public static Integer getLoggedInUserId() {
        return userId;
    }

    public boolean parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(API_KEY))
                    .parseClaimsJws(jwt).getBody();
            Date date = new Date();
            if (claims.getExpiration().before(date)) {
                return false;
            }
            userId = Integer.parseInt(claims.getId());
            permissionDetails = claims.get("userPermission").toString();
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    public static Boolean isAuthorizedUser(String url) {
        try {
            url = url.substring(url.lastIndexOf("/"));
            JSONObject permissionsJson = new JSONObject(permissionDetails);
            if (Objects.isNull(permissionsJson)) {
                log.info("permission details is not present for the user");
                return false;
            }
            boolean isAuthorizedUser = permissionsJson.has("ncr_config_panel")
                    && permissionsJson.getJSONObject("ncr_config_panel").has("permission")
                    && permissionsJson.getJSONObject("ncr_config_panel").getJSONObject("permission").has(url)
                    && permissionsJson.getJSONObject("ncr_config_panel").getJSONObject("permission").getBoolean(url)
                    ? Boolean.TRUE
                    : Boolean.FALSE;
            return isAuthorizedUser;
        } catch (JSONException e) {
            log.error("JSONException" + e);
            return false;
        } catch (Exception e) {
            log.error("Exception :: " + e);
            return false;
        }
    }

}
