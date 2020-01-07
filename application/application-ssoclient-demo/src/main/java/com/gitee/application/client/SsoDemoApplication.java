package com.gitee.application.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * @author lihaifeng
 * 登录http://localhost:8030/token/login
 * 输入账号密码 lihaifeng 123456
 */
@EnableOAuth2Sso
@SpringBootApplication
public class SsoDemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(SsoDemoApplication.class, args);
  }
}
