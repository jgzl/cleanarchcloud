/*
 * Copyright (c) 2019-2019
 */

package com.gitee.application;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.gitee.annotation.EnableSwaggerPlus;

/**
 * @author lihaifeng
 */
@EnableSwaggerPlus
@DubboComponentScan
@EnableDiscoveryClient
@SpringBootApplication
public class DubboTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboTemplateApplication.class, args);
	}
}
