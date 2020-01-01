package com.gitee.application.auth.service;

import javax.sql.DataSource;

import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

/**
 * @author lihaifeng
 */
public class PlatformClientUserDetailsServiceImpl extends JdbcClientDetailsService {
  public PlatformClientUserDetailsServiceImpl(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
    return super.loadClientByClientId(clientId);
  }
}
