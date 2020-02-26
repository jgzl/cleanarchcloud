/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gitee.common.security.component;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import com.gitee.common.core.constant.SecurityConstants;
import com.gitee.common.security.vo.SsoUserVO;

/**
 * @author lihaifeng
 */
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

	public CustomAccessTokenConverter() {
		super.setUserTokenConverter(new CustomerUserAuthenticationConverter());
	}

	private class CustomerUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

		@Override
		public Map<String, ?> convertUserAuthentication(Authentication authentication) {
			final Map<String, Object> response = new LinkedHashMap<>();
			response.put(USERNAME, authentication.getName());
			response.put(SecurityConstants.LICENSE_KEY, SecurityConstants.LICENSE);
			response.put(SecurityConstants.USER_NAME_HEADER, authentication.getName());
			if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
				response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
			}
			if (authentication.getPrincipal() instanceof SsoUserVO) {
				final SsoUserVO user = (SsoUserVO) authentication.getPrincipal();
				response.put(SecurityConstants.USER_ID_HEADER, user.getUserId());
			}
			return response;
		}
	}
}
