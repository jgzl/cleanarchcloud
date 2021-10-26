package com.github.jgzl.infra.upms.login.config;
import com.xkcoding.justauth.autoconfigure.JustAuthProperties;
import com.xkcoding.justauth.support.cache.RedisStateCache;
import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AuthStateConfiguration {
	@Bean
	public AuthStateCache authStateCache(RedisTemplate<String, String> redisTemplate, JustAuthProperties justAuthProperties) {
		return new RedisStateCache(redisTemplate,justAuthProperties.getCache());
	}
}
