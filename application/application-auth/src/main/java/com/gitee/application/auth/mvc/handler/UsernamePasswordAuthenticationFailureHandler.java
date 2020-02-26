package com.gitee.application.auth.mvc.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.common.core.util.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户名密码登录失败handler
 *
 * @author lihaifeng
 * 2019/7/3 14:42
 * @see SimpleUrlAuthenticationFailureHandler
 */
@Component
@Slf4j
public class UsernamePasswordAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("UsernamePasswordAuthenticationFailureHandler:" + exception.getMessage());
        }
        final Response resp = Response.failure(exception.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(resp));
    }
}
