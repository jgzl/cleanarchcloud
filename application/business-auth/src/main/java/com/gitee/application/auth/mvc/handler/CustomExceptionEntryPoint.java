package com.gitee.application.auth.mvc.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.common.core.util.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义token校验失败返回信息: 通用
 *
 * @author lihaifeng
 * 2019/5/6 10:54
 */
@Component
@Slf4j
public class CustomExceptionEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException,
			ServletException {
		if (log.isDebugEnabled()) {
			log.debug("CustomExceptionEntryPoint:" + authException.getMessage());
		}
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(objectMapper.writeValueAsString(Result.failed(authException.getMessage())));
	}
}
