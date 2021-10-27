package com.github.jgzl.infra.upms;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.feign.annotation.EnableFeignClientsPlus;
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
@EnableDiscoveryClient
@SpringBootApplication
@EnableRedisHttpSession(redisNamespace = CacheConstants.REDIS_SESSION_PREFIX+CacheConstants.SPLIT+"auth")
public class UpmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(UpmsApplication.class, args);
	}
}
