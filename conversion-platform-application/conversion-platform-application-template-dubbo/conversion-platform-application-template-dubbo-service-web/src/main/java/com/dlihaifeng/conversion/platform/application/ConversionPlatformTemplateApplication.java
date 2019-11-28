/*
 * Copyright (c) 2019-2019
 */

package com.dlihaifeng.conversion.platform.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lihaifeng
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ConversionPlatformTemplateApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConversionPlatformTemplateApplication.class, args);
  }
}
