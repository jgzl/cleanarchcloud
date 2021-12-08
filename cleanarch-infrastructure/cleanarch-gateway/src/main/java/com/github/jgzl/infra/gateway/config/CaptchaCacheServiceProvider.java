package com.github.jgzl.infra.gateway.config;

import com.anji.captcha.service.CaptchaCacheService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author lihaifeng
 * @date 2020/8/27
 * <p>
 * 验证码 缓存提供支持集群,需要通过SPI
 */
public class CaptchaCacheServiceProvider implements CaptchaCacheService {

	private static final String REDIS = "redis";

	@Autowired
	private RedissonClient redisson;

	@Override
	public void set(String key, String value, long expiresInSeconds) {
		redisson.getBucket(key).set(value, expiresInSeconds, TimeUnit.SECONDS);
	}

	@Override
	public boolean exists(String key) {
		return redisson.getKeys().countExists(key)>0;
	}

	@Override
	public void delete(String key) {
		redisson.getKeys().delete(key);
	}

	@Override
	public String get(String key) {
		return (String) redisson.getBucket(key).get();
	}

	@Override
	public String type() {
		return REDIS;
	}

}
