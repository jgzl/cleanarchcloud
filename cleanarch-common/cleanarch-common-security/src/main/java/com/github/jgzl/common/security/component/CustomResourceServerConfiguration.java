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

package com.github.jgzl.common.security.component;

import com.github.jgzl.common.core.config.SysProperties;
import com.github.jgzl.common.core.constant.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.List;

/**
 * @author lihaifeng
 * @date 2018/6/22
 * <p>
 * 1. 支持remoteTokenServices 负载均衡
 * 2. 支持 获取用户全部信息
 */
@Slf4j
public class CustomResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Autowired
	private CustomResourceAuthExceptionEntryPoint authExceptionEntryPoint;

	@Autowired
	private SysProperties ssoProperties;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
				.authorizeRequests();
		final List<String> urls = ssoProperties.getOauth2().getUrlPermitAll();
		urls.forEach(url -> registry.antMatchers(url).permitAll());
		registry.anyRequest().authenticated().and().cors().and().csrf().disable();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
		UserAuthenticationConverter userTokenConverter = new CustomUserAuthenticationConverter();
		accessTokenConverter.setUserTokenConverter(userTokenConverter);
		resources.authenticationEntryPoint(authExceptionEntryPoint);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public TokenStore tokenStore() {
		final RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenStore.setSerializationStrategy(new CustomStringSerializationStrategy());
		tokenStore.setPrefix(CacheConstants.REDIS_TOKEN_PREFIX);
		return tokenStore;
	}
}
