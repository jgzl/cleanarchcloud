package com.github.jgzl.application.auth.custom.login.filter.username;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jgzl.common.security.vo.AuthenticationBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 重写 UsernamePasswordAuthenticationFilter 类, 支持实现异步JSON登录
 *
 * @author lihaifeng
 * 2019/7/3 14:09
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 */
@Slf4j
public class UsernamePasswordAuthenticationFilter extends org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
        AuthenticationException {

        // 如果是JSON登录
        UsernamePasswordAuthenticationToken authRequest;
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();

            try (InputStream is = request.getInputStream()) {
                AuthenticationBean authenticationBean = mapper.readValue(is, AuthenticationBean.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword());
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                log.error("异步登陆失败", e);
            }
        } else {
            // 支持原来默认的登录方式
            return super.attemptAuthentication(request, response);
        }
        authRequest = new UsernamePasswordAuthenticationToken("", "");
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
