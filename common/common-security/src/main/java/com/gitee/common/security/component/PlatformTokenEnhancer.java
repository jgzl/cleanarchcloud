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

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.gitee.common.core.constant.SecurityConstants;
import com.gitee.common.security.login.PlatformUser;

/**
 * @author lihaifeng
 */
@Component
public class PlatformTokenEnhancer implements TokenEnhancer {
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		if (SecurityConstants.CLIENT_CREDENTIALS.equals(authentication.getOAuth2Request().getGrantType())) {
			return accessToken;
		}

		final Map<String, Object> additionalInfo = new HashMap<>(8);
		PlatformUser platformUser = (PlatformUser) authentication.getUserAuthentication().getPrincipal();
		additionalInfo.put(SecurityConstants.DETAILS_USER_ID, platformUser.getId());
		additionalInfo.put(SecurityConstants.DETAILS_USERNAME, platformUser.getUsername());
		additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.PLATFORM_LICENSE);
		additionalInfo.put(SecurityConstants.ACTIVE, Boolean.TRUE);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}
}
