package com.github.jgzl.application.auth.custom.login.service;

import com.github.jgzl.common.core.constant.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * 自定义 ClientDetailsService
 *
 * @author lihaifeng
 * 2019/7/23 16:39
 */
@Slf4j
public class CustomClientDetailsService extends JdbcClientDetailsService {

	public CustomClientDetailsService(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	@Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(final String clientId) throws InvalidClientException {
        final ClientDetails clientDetails = super.loadClientByClientId(clientId);
        return clientDetails;
    }
}
