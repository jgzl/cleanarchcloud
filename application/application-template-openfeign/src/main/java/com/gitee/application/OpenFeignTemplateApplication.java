/*
 * Copyright (c) 2019-2019
 */

package com.gitee.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.gitee.annotation.EnableSwaggerPlus;

/**
 * @author lihaifeng
 */
@EnableSwaggerPlus
@EnableDiscoveryClient
@SpringBootApplication
public class OpenFeignTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenFeignTemplateApplication.class, args);
	}
}
