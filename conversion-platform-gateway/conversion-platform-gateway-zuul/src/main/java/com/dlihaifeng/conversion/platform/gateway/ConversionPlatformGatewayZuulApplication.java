/*
 * Copyright (c) 2019-2019
 */

package com.dlihaifeng.conversion.platform.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lihaifeng
 */
@EnableZuulProxy
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ConversionPlatformGatewayZuulApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConversionPlatformGatewayZuulApplication.class, args);
  }
}
