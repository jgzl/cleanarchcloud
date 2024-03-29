package com.github.jgzl.infra.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * @author lihaifeng
 */
@Slf4j
@Component
public class TimeStatsGlobalFilter implements GlobalFilter, Ordered {

	private static final String COUNT_START_TIME = "countStartTime";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		exchange.getAttributes().put(COUNT_START_TIME, Instant.now().toEpochMilli());
		if (log.isDebugEnabled()) {
			exchange.getRequest().getHeaders().forEach((key, value) -> {
				log.debug("header为[{}:{}]", key, value);
			});
		}
		return chain.filter(exchange).then(
				Mono.fromRunnable(() -> {
					long startTime = exchange.getAttribute(COUNT_START_TIME);
					long endTime = (Instant.now().toEpochMilli() - startTime);
					log.info("访问[{}]共消耗时间为:{}ms", exchange.getRequest().getURI().getRawPath(), endTime);
				})
		);
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
