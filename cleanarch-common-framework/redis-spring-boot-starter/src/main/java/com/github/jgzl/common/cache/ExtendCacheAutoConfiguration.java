package com.github.jgzl.common.cache;

import com.github.jgzl.common.cache.interceptor.*;
import com.github.jgzl.common.cache.properties.ExtendCacheConfigProperties;
import com.github.jgzl.common.cache.sequence.RedisSequenceHelper;
import org.redisson.api.RedissonClient;
import org.redisson.codec.FstCodec;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * redis限流器自动配置项
 *
 * @author lihaifeng
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(ExtendCacheConfigProperties.class)
@ConditionalOnProperty(prefix = ExtendCacheConfigProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class ExtendCacheAutoConfiguration {

	@Bean
	public RedisKeyGenerator redisKeyGenerator() {
		return new DefaultRedisKeyGenerator();
	}

	@Bean
	public RedisSequenceHelper redisSequenceHelper(RedissonClient redisson) {
		return new RedisSequenceHelper(redisson);
	}

	@Bean
	@ConditionalOnProperty(prefix = ExtendCacheConfigProperties.PREFIX + ".limit", name = "enabled", havingValue = "true", matchIfMissing = true)
	public RedisLimitHelper redisLimitHelper(RedissonClient redissonClient) {
		return new RedisLimitHelper(redissonClient);
	}

	@Bean
	@ConditionalOnProperty(prefix = ExtendCacheConfigProperties.PREFIX + ".lock", name = "enabled", havingValue = "true", matchIfMissing = true)
	public RedisLockInterceptor redissonLockAspect(RedissonClient redissonClient, RedisKeyGenerator redisKeyGenerator) {
		return new RedisLockInterceptor(redissonClient, redisKeyGenerator);
	}

	@Bean
	@ConditionalOnBean(RedisLimitHelper.class)
	@ConditionalOnProperty(prefix = ExtendCacheConfigProperties.PREFIX + ".limit", name = "enabled", havingValue = "true", matchIfMissing = true)
	public RedisLimitInterceptor redisLimitInterceptor(RedisLimitHelper redisLimitHelper) {
		return new RedisLimitInterceptor(redisLimitHelper);
	}

	@Bean
	@ConditionalOnMissingBean
	public CacheManager cacheManager(RedissonClient redisson) {
		Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
		// 创建一个名称为"RedissonSpringCacheManager"的缓存，过期时间ttl为24分钟，同时最长空闲时maxIdleTime为12分钟。
		config.put("RedissonSpringCacheManager", new CacheConfig(24 * 60 * 1000, 12 * 60 * 1000));
		return new RedissonSpringCacheManager(redisson, config, new FstCodec());
	}
}
