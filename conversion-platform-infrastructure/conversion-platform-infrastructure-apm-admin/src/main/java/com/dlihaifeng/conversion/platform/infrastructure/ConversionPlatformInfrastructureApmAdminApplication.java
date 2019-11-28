/*
 * Copyright (c) 2019-2019
 */

package com.dlihaifeng.conversion.platform.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * @author lihaifeng
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class ConversionPlatformInfrastructureApmAdminApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConversionPlatformInfrastructureApmAdminApplication.class, args);
  }
}
