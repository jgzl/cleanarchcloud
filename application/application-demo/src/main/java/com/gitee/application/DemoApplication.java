/*
 * Copyright (c) 2019-2019
 */

package com.gitee.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.gitee.annotation.EnableSwaggerPlus;

/**
 * @author lihaifeng
 */
@EnableSwaggerPlus
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
