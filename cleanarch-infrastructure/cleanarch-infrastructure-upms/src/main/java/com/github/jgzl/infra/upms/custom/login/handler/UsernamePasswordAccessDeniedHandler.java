package com.github.jgzl.infra.upms.custom.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jgzl.common.core.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义授权失败返回信息: 通用
 *
 * @author lihaifeng
 * 2019/5/6 11:02
 */
@Component
@Slf4j
public class UsernamePasswordAccessDeniedHandler implements AccessDeniedHandler {

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
