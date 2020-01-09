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

package com.gitee.application.auth.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.gitee.common.core.constant.SecurityConstants;
import com.gitee.common.security.login.PlatformClientUserDetailsServiceImpl;

import lombok.AllArgsConstructor;

/**
 * @author lihaifeng
 */
@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class PlatformAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  private DataSource dataSource;

  private AuthenticationManager authenticationManager;

  private UserDetailsService userDetailsService;

  private TokenEnhancer tokenEnhancer;

  private RedisConnectionFactory redisConnectionFactory;

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService)
        .tokenEnhancer(tokenEnhancer)
        .tokenStore(tokenStore())
        .reuseRefreshTokens(false)
        .pathMapping("/oauth/confirm_access", "/token/confirm_access");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    PlatformClientUserDetailsServiceImpl clientUserDetailsService = new PlatformClientUserDetailsServiceImpl(
        dataSource);
    clientUserDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
    clientUserDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
    clients.withClientDetails(clientUserDetailsService);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.allowFormAuthenticationForClients()
        .checkTokenAccess("isAuthenticated()");
  }

  @Bean
  public RedisTokenStore tokenStore() {
    RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
    redisTokenStore.setPrefix(SecurityConstants.PLATFORM_PREFIX+SecurityConstants.OAUTH_PREFIX);
    return redisTokenStore;
  }
}
