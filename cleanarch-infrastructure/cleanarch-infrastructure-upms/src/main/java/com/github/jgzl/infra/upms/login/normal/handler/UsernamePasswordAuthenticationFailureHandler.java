package com.github.jgzl.infra.upms.login.normal.handler;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jgzl.common.core.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 用户名密码登录失败handler
 *
 * @author lihaifeng
 * 2019/7/3 14:42
 * @see SimpleUrlAuthenticationFailureHandler
 */
@Slf4j
@Component
public class UsernamePasswordAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException {
		log.info("UsernamePasswordAuthenticationFailureHandler:",exception);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		if (exception instanceof BadCredentialsException) {
			response.getWriter().write(JSONUtil.toJsonStr(Result.failed("密码错误")));
		}else {
			response.getWriter().write(JSONUtil.toJsonStr(Result.failed(exception.getMessage())));
		}
	}
}
