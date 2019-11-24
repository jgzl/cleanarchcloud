/*
 * Copyright (c) 2019-2019
 */

package com.dlihaifeng.conversion.platform.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author lihaifeng
 */
@Configuration
public class SpringCloudGatewayConfig {
  /*  @Bean
    public RoutePredicateFactory routePredicateFactory() {
      return new DemoRoutePredicateFactory();
    }

    @Bean
    public RequestGlobalFilter requestGatewayFilter() {
      return new RequestGlobalFilter();
    }

    @Bean
    public CustomerGatewayFilter customerGatewayFilter() {
      return new CustomerGatewayFilter();
    }

    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
      return builder.routes()
          .route(r -> r.path("/customer/**")
              .filters(f -> f.filter(new CustomerGatewayFilter())
                  .addResponseHeader("X-Response-test", "test"))
              .uri("http://httpbin.org:80/get")
              .id("customer_filter_router")
          )
          .build();
    }*/

  /**
   * ribbon负载均衡
   * @return
   */
  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
