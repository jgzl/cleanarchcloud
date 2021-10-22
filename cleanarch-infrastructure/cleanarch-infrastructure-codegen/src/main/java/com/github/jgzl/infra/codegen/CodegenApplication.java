package com.github.jgzl.infra.codegen;

import com.github.jgzl.common.feign.annotation.EnableFeignClientsPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.github.jgzl.common.security.annotation.EnableResourceServerPlus;

/**
 * @author lihaifeng
 */
@EnableFeignClientsPlus
@EnableDiscoveryClient
@SpringBootApplication
@EnableResourceServerPlus
public class CodegenApplication {
	public static void main(String[] args) {
		SpringApplication.run(CodegenApplication.class, args);
	}
}
