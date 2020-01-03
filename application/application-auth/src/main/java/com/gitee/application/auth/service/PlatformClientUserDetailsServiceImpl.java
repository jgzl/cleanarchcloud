package com.gitee.application.auth.service;

import javax.sql.DataSource;

import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import com.gitee.common.core.constant.SecurityConstants;

/**
 * @author lihaifeng
 */
public class PlatformClientUserDetailsServiceImpl extends JdbcClientDetailsService {

  public PlatformClientUserDetailsServiceImpl(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
    BaseClientDetails user = (BaseClientDetails) super.loadClientByClientId(clientId);
    user.setClientSecret(SecurityConstants.BCRYPT + user.getClientSecret());
    return user;
  }
}
