package com.gitee.application.auth.mvc.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.common.core.util.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义授权失败返回信息: 通用
 *
 * @author lihaifeng
 * 2019/5/6 11:02
 */
@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		if (log.isDebugEnabled()) {
			log.debug("CustomAccessDeniedHandler:" + accessDeniedException.getMessage());
		}
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(objectMapper.writeValueAsString(Result.failed(accessDeniedException.getMessage())));
	}
}
