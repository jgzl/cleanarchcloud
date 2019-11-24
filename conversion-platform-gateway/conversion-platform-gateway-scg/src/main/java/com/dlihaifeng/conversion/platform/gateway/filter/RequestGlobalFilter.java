/*
 * Copyright (c) 2019-2019
 *//*


package com.dlihaifeng.conversion.platform.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

*/
/**
 * @author lihaifeng
 *//*

@Slf4j
public class RequestGlobalFilter implements GlobalFilter, Ordered {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    log.info("request:{}", request.getURI());
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
*/
