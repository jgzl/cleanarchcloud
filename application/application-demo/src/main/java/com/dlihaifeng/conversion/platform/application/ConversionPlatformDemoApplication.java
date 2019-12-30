/*
 * Copyright (c) 2019-2019
 */

package com.dlihaifeng.conversion.platform.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lihaifeng
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ConversionPlatformDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConversionPlatformDemoApplication.class, args);
  }
}
