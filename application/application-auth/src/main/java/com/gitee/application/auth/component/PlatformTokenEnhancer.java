package com.gitee.application.auth.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.gitee.application.auth.model.PlatformUser;
import com.gitee.common.core.constant.SecurityConstants;

/**
 * @author lihaifeng
 */
@Component
public class PlatformTokenEnhancer implements TokenEnhancer {
  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    if (SecurityConstants.CLIENT_CREDENTIALS
        .equals(authentication.getOAuth2Request().getGrantType())) {
      return accessToken;
    }

    final Map<String, Object> additionalInfo = new HashMap<>(8);
    PlatformUser platformUser = (PlatformUser) authentication.getUserAuthentication().getCredentials();
    additionalInfo.put(SecurityConstants.DETAILS_USER_ID, platformUser.getId());
    additionalInfo.put(SecurityConstants.DETAILS_USERNAME, platformUser.getUsername());
    additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.PIGX_LICENSE);
    additionalInfo.put(SecurityConstants.ACTIVE, Boolean.TRUE);
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    return accessToken;
  }
}
