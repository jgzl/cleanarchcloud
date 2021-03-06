/*
 * Copyright (c) 2019-2019
 */

package com.github.jgzl.application;

import com.github.jgzl.swagger.annotation.EnableSwaggerPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.github.jgzl.common.security.annotation.EnableFeignClientsPlus;

/**
 * @author lihaifeng
 */
@EnableSwaggerPlus
@EnableFeignClientsPlus
@EnableDiscoveryClient
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
