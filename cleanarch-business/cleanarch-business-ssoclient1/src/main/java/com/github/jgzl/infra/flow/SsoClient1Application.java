package com.github.jgzl.infra.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.github.jgzl.common.security.annotation.EnableFeignClientsPlus;

/**
 * @author lihaifeng
 * 登录http://localhost:8010/login/username/login
 * 输入账号密码 admin admin
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClientsPlus
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SsoClient1Application {
	public static void main(String[] args) {
		SpringApplication.run(SsoClient1Application.class, args);
	}
}
