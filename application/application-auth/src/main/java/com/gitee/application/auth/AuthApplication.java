package com.gitee.application.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.gitee.annotation.EnableSwaggerPlus;

/**
 * @author lihaifeng
 * 登录http://localhost:8030/token/login
 * 输入账号密码 lihaifeng 123456
 */
@EnableSwaggerPlus
@EnableDiscoveryClient
@SpringBootApplication
public class AuthApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthApplication.class, args);
  }
}
