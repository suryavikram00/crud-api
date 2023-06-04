package com.crud.api.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.crud.api.utility.JwtTokenUtils;
import com.crud.api.nuttycrunch.entity.NcrConfigEntity;
import com.crud.api.nuttycrunch.service.NcrConfigService;
import com.crud.api.utility.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(1)
public class AuthenticationFilter implements Filter {

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    NcrConfigService ncrConfigService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        HttpServletResponse httpServletResponse = ((HttpServletResponse) response);

        try {
            if (CommonUtils.canSkipFilter(httpServletRequest.getRequestURI())) {
                chain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            String jwtToken = "";
            if (Objects.nonNull(httpServletRequest.getHeader("token"))) {
                jwtToken = httpServletRequest.getHeader("token");
            }
            Boolean isValidToken = jwtTokenUtils.parseJWT(jwtToken);
            List<String> configList = new ArrayList<>();
            configList.add("jwtToken");
            List<NcrConfigEntity> ncrConfigEntitiesList = ncrConfigService.getConfigsByConfigNames(configList);
            String configValue = "";
            if (ncrConfigEntitiesList != null && ncrConfigEntitiesList.size() > 0) {
                configValue = ncrConfigEntitiesList.get(0).getConfigValue();
            } else {
                configValue = null;
            }

            if (Objects.equals(isValidToken, true) || jwtToken.equals(configValue)) {
                chain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.warn("Exception occured Inside TokenAuthFilter::doFilter: {}", e.getMessage());
        }

    }

}
