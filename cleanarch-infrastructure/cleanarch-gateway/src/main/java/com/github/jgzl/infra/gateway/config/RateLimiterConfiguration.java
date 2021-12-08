package com.github.jgzl.infra.gateway.config;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;
import java.util.Objects;
/**
 * 路由限流配置
 *
 * @author lihaifeng
 */
@Configuration
public class RateLimiterConfiguration {
	private static final String AUTHORIZATION = "authorization";
	/**
	 * IP 限流
	 *
	 * @return IP限流
	 */
	@Primary
	@Bean(name = "ipKeyResolver")
	public KeyResolver ipKeyResolver() {
		return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName());
	}
	/**
	 * 用户限流
	 * 使用这种方式限流，请求路径中必须携带userId参数
	 *
	 * @return 用户限流
	 */
	@Bean(name = "tokenKeyResolver")
	public KeyResolver tokenKeyResolver() {
		return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getHeaders().getFirst(AUTHORIZATION)));
	}
	/**
	 * 接口限流
	 * 获取请求地址的uri作为限流key
	 *
	 * @return 接口限流
	 */
	@Bean(name = "apiKeyResolver")
	public KeyResolver apiKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getPath().value());
	}

	@Bean(value = "remoteAddrKeyResolver")
	public KeyResolver remoteAddrKeyResolver() {
		return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
	}
}
