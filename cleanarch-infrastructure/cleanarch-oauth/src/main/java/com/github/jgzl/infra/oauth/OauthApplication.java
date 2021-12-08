package com.github.jgzl.infra.oauth;

import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.feign.annotation.EnableFeignClientsPlus;
import com.github.jgzl.common.websocket.redis.EnableRedisWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author lihaifeng
 * 登录 http://localhost:8010/login/username/login
 * 输入账号密码 admin admin
 */
@EnableFeignClientsPlus
@EnableRedisWebSocket
@EnableDiscoveryClient
@SpringBootApplication
@EnableRedisHttpSession(redisNamespace = ServiceNameConstants.OAUTH_SERVICE)
public class OauthApplication {
	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
	}
}
