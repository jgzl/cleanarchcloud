package com.github.jgzl.common.security.component;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.github.jgzl.common.core.util.Result;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author lihaifeng
 * @date 2020/7/8
 * 客户端异常处理
 * 1. 可以根据 AuthenticationException 不同细化异常处理
 */
@Slf4j
@Component
@AllArgsConstructor
public class ExtendResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

	@Override
	@SneakyThrows
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) {
		log.info("====================自定义资源登录异常====================");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		Result<String> result = Result.fail("自定义资源登录异常");
		if (authException != null) {
			result.setMessage(authException.getMessage());
			result.setData(authException.getMessage());
		}
		response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
		response.getWriter().write(JSONUtil.toJsonStr(result));
	}
}
