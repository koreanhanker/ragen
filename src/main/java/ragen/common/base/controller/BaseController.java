package ragen.common.base.controller;

import ragen.common.util.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class BaseController {
    public LoginUser loginUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        LoginUser usrInfo = (LoginUser) request.getAttribute("LoginUser");

        if(usrInfo == null){
            return new LoginUser();
        }

        return usrInfo;
    }
}
