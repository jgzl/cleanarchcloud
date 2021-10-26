package com.github.jgzl.infra.upms.login.config;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.github.jgzl.common.core.constant.CommonConstants;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.api.vo.UserVo;
import com.github.jgzl.common.security.exception.CustomOauth2Exception;
import com.github.jgzl.common.security.util.SecurityMessageSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 自定义token的转换
 * 默认使用DefaultAccessTokenConverter 这个装换器
 * DefaultAccessTokenConverter有个UserAuthenticationConverter，这个转换器作用是把用户的信息放入token中，
 * 默认只是放入username, 重写这个方法放入额外的信息
 *
 * @author lihaifeng
 * 2019/4/25 10:44
 */
@Slf4j
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

	public CustomAccessTokenConverter() {
		super.setUserTokenConverter(new CustomUserAuthenticationConverter());
	}

	private static class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

		private static final String N_A = "N/A";

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

		/**
		 * Inverse of {@link #convertUserAuthentication(Authentication)}. Extracts an
		 * Authentication from a map.
		 * @param responseMap a map of user information
		 * @return an Authentication representing the user or null if there is none
		 */
		@Override
		public Authentication extractAuthentication(Map<String, ?> responseMap) {
			if (responseMap.containsKey(USERNAME)) {
				Collection<? extends GrantedAuthority> authorities = getAuthorities(responseMap);
				Map<String, ?> map = MapUtil.get(responseMap, SecurityConstants.DETAILS_USER, Map.class);
				validateTenantId(map);
				String username = MapUtil.getStr(map, SecurityConstants.DETAILS_USERNAME);
				Integer id = MapUtil.getInt(map, SecurityConstants.DETAILS_USER_ID);
				Integer tenantId = MapUtil.getInt(map, SecurityConstants.DETAILS_TENANT_ID);
				String phone = MapUtil.getStr(map, SecurityConstants.DETAILS_PHONE);
				String avatar = MapUtil.getStr(map, SecurityConstants.DETAILS_AVATAR);
				UserVo user = UserVo.builder()
						.userId(id+"")
						.username(username)
						.avatar(avatar)
						.mobile(phone)
						.tenantId(tenantId)
						.build();
				return new UsernamePasswordAuthenticationToken(user, N_A, authorities);
			}
			return null;
		}

		private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
			Object authorities = map.get(AUTHORITIES);
			if (authorities instanceof String) {
				return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
			}
			if (authorities instanceof Collection) {
				return AuthorityUtils.commaSeparatedStringToAuthorityList(
						StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
			}
			return AuthorityUtils.NO_AUTHORITIES;
		}

		private void validateTenantId(Map<String, ?> map) {
			String headerValue = getCurrentTenantId();
			Integer userValue = MapUtil.getInt(map, SecurityConstants.DETAILS_TENANT_ID);
			if (StrUtil.isNotBlank(headerValue) && !userValue.toString().equals(headerValue)) {
				log.warn("请求头中的租户ID({})和用户的租户ID({})不一致", headerValue, userValue);
				// TODO: 不要提示租户ID不对，可能被穷举
				throw new CustomOauth2Exception(SecurityMessageSourceUtil.getAccessor().getMessage(
						"AbstractUserDetailsAuthenticationProvider.badTenantId", new Object[] { headerValue },
						"Bad tenant ID"));
			}
		}

		private Optional<HttpServletRequest> getCurrentHttpRequest() {
			return Optional.ofNullable(RequestContextHolder.getRequestAttributes()).filter(
							requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
					.map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
					.map(ServletRequestAttributes::getRequest);
		}

		private String getCurrentTenantId() {
			return getCurrentHttpRequest()
					.map(httpServletRequest -> httpServletRequest.getHeader(CommonConstants.TENANT_ID)).orElse(null);
		}
    }
}
