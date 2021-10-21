package com.github.jgzl.infra.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 路由限流配置
 *
 * @author pangu
 * @since 2.3.8
 */
@Configuration
public class RateLimiterConfig {
	@Bean(value = "remoteAddrKeyResolver")
	public KeyResolver remoteAddrKeyResolver() {
		return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
	}
}
