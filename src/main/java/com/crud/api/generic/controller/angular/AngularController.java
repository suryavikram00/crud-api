/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.generic.controller.angular;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author NMSLAP570
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Slf4j
@Controller
public class AngularController {

    @RequestMapping({"/crud-panel/**", "/crud-panel"})
    public String angularApp(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith(".js") || requestURI.endsWith(".css")) {
            return "forward:/static" + requestURI;
        } else {
            return "forward:/static/crud-panel/index.html";
        }
    }

}
