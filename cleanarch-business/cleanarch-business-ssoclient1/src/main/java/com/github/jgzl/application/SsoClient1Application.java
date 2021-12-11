package com.github.jgzl.application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
/**
 * @author lihaifeng
 * 登录http://localhost:8011/token/login
 * 输入账号密码 admin 123456
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableRedisHttpSession(redisNamespace = "cleanarch-ssoclient1")
public class SsoClient1Application {
	public static void main(String[] args) {
		SpringApplication.run(SsoClient1Application.class, args);
	}
}
