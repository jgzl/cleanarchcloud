package com.github.jgzl.common.cache;

import com.github.jgzl.common.cache.interceptor.*;
import com.github.jgzl.common.cache.properties.RedisPlusProperties;
import com.github.jgzl.common.cache.sequence.RedisSequenceHelper;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redisson限流器自动配置项
 *
 * @author Levin
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(RedisPlusProperties.class)
@ConditionalOnProperty(prefix = RedisPlusProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class ExtendCacheAutoConfiguration {

    @Bean
    @Primary
    public RedisKeyGenerator redisKeyGenerator() {
        return new DefaultRedisKeyGenerator();
    }

    @Bean
    @Primary
    public RedisSequenceHelper redisSequenceHelper(StringRedisTemplate stringRedisTemplate) {
        return new RedisSequenceHelper(stringRedisTemplate);
    }

    @Bean
    @ConditionalOnProperty(prefix = RedisPlusProperties.PREFIX+".limit", name = "enabled", havingValue = "true", matchIfMissing = true)
    public RedisLimitHelper redisLimitHelper(RedissonClient redissonClient) {
        return new RedisLimitHelper(redissonClient);
    }

    @Bean
    @ConditionalOnProperty(prefix = RedisPlusProperties.PREFIX+".lock", name = "enabled", havingValue = "true", matchIfMissing = true)
    public RedisLockInterceptor redissonLockAspect(RedissonClient redissonClient, RedisKeyGenerator redisKeyGenerator) {
        return new RedisLockInterceptor(redissonClient, redisKeyGenerator);
    }

    @Bean
    @ConditionalOnBean(RedisLimitHelper.class)
    @ConditionalOnProperty(prefix = RedisPlusProperties.PREFIX+".limit", name = "enabled", havingValue = "true", matchIfMissing = true)
    public RedisLimitInterceptor redisLimitInterceptor(RedisLimitHelper redisLimitHelper) {
        return new RedisLimitInterceptor(redisLimitHelper);
    }


}
