package com.github.jgzl.common.security.component;

import com.github.jgzl.common.core.constant.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class ExtendSecurityConfiguration {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	/**
	 * 加密器 spring boot 2.x没有默认的加密器了
	 *
	 * @return PasswordEncoder
	 */
	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public TokenStore tokenStore() {
		final RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
//		tokenStore.setSerializationStrategy(new ExtendStringSerializationStrategy());
		tokenStore.setPrefix(CacheConstants.REDIS_TOKEN_PREFIX);
		return tokenStore;
	}
}
