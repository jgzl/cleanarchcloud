package com.github.jgzl.application.codegen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.github.jgzl.common.security.annotation.EnableFeignClientsPlus;
import com.github.jgzl.common.security.annotation.EnableResourceServerPlus;

/**
 * @author lihaifeng
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClientsPlus
@EnableResourceServerPlus
public class CodegenApplication {
	public static void main(String[] args) {
		SpringApplication.run(CodegenApplication.class, args);
	}
}
