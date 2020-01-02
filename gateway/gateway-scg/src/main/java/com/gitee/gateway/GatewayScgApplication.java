/*
 * Copyright (c) 2019-2019
 */

package com.gitee.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lihaifeng
 */
//@EnableDynamicSpringCloudGateway
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayScgApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayScgApplication.class, args);
  }
}
