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
          .route("demo_route",
              r -> r.path("/demodemo")
                  .uri("lb://conversion-platform-application-demo/")
                  .filters())
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
