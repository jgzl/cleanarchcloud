package com.github.jgzl.application.auth.oauth2.handler;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jgzl.common.core.constant.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 手机号登录成功, 直接返回token
 *
 * @author lihaifeng
 * 2019/5/15 16:03
 * @see SavedRequestAwareAuthenticationSuccessHandler
 */
@Slf4j
@Data
@Component
@AllArgsConstructor
public class MobileTokenLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    private final ClientDetailsService clientDetailsService;

    private final DefaultTokenServices tokenServices;

	@Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException,
			ServletException {
        final String header = request.getHeader(SecurityConstants.AUTHORIZATION);

        if (header == null || !header.startsWith(SecurityConstants.BASIC_HEADER)) {
            throw new UnapprovedClientAuthenticationException("请求头中client信息为空");
        }
        try {
            final String[] tokens = extractAndDecodeHeader(header);
            assert tokens.length == 2;
            final String clientId = tokens[0];

            final ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
            final TokenRequest tokenRequest = new TokenRequest(MapUtil.newHashMap(), clientId, clientDetails.getScope(), "mobile");
            final OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

            final OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            final OAuth2AccessToken oAuth2AccessToken = tokenServices.createAccessToken(oAuth2Authentication);
            if (log.isDebugEnabled()) {
                log.debug("MobileLoginSuccessHandler 签发token 成功：{}", oAuth2AccessToken.getValue());
            }

            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            final PrintWriter printWriter = response.getWriter();
            printWriter.append(objectMapper.writeValueAsString(oAuth2AccessToken));
        } catch (IOException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
    }

    /**
     * Decodes the header into a username and password.
     *
     * @throws BadCredentialsException if the Basic header is not present or is not valid
     *                                 Base64
     */
    private String[] extractAndDecodeHeader(String header) throws IOException {
        final byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        final String token = new String(decoded, StandardCharsets.UTF_8);

        final int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] {token.substring(0, delim), token.substring(delim + 1)};
    }
}
