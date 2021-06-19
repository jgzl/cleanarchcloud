package com.github.jgzl.application.auth.normal.config;

import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.security.vo.UserVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义token的转换
 * 默认使用DefaultAccessTokenConverter 这个装换器
 * DefaultAccessTokenConverter有个UserAuthenticationConverter，这个转换器作用是把用户的信息放入token中，
 * 默认只是放入username, 重写这个方法放入额外的信息
 *
 * @author lihaifeng
 * 2019/4/25 10:44
 */
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

	public CustomAccessTokenConverter() {
		super.setUserTokenConverter(new CustomUserAuthenticationConverter());
	}

	private class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

		@Override
		public Map<String, ?> convertUserAuthentication(Authentication authentication) {
			final Map<String, Object> response = new LinkedHashMap<>();
			response.put(USERNAME, authentication.getName());
			response.put(SecurityConstants.LICENSE_KEY, SecurityConstants.LICENSE);
			response.put(SecurityConstants.USER_NAME_HEADER, authentication.getName());
			if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
				response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
			}
			if (authentication.getPrincipal() instanceof UserVo) {
				final UserVo user = (UserVo) authentication.getPrincipal();
				response.put(SecurityConstants.USER_ID_HEADER, user.getUserId());
			}
			return response;
		}
    }
}
