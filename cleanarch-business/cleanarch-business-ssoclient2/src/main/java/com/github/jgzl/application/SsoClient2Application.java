package com.github.jgzl.application;
import com.github.jgzl.common.core.constant.CacheConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author lihaifeng
 * 登录http://localhost:8010/login/username/login
 * 输入账号密码 admin admin
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableRedisHttpSession(redisNamespace = CacheConstants.REDIS_SESSION_PREFIX+CacheConstants.SPLIT+"ssoclient1")
public class SsoClient2Application {
	public static void main(String[] args) {
		SpringApplication.run(SsoClient2Application.class, args);
	}
}
