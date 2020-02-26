package com.gitee.application.auth.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lihaifeng
 * @date 2018年03月10日
 */
@Controller
@RequestMapping("/oauth")
public class TokenController {

    /**
     * 退出
     */
    @GetMapping("/exit")
    public void removeToken(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            //sending back to client app
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
