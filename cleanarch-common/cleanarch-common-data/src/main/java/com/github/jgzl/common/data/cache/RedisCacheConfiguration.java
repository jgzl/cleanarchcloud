package com.github.jgzl.common.data.cache;

import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.util.SpringContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis缓存配置
 * @author lihaifeng
 */
@Slf4j
@EnableCaching
@Configuration
@AllArgsConstructor
@AutoConfigureBefore(name = { "org.redisson.spring.starter.RedissonAutoConfiguration",
		"org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration" })
public class RedisCacheConfiguration {

	/**
	 * redis template 高级配置
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	/**
	 * redis自定义操作仓库
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public CustomRedisRepository customRedisRepository(RedisTemplate<String,String> redisTemplate){
		return new CustomRedisRepository(redisTemplate);
	}

	/**
	 * redis 监听配置
	 * @param redisConnectionFactory redis 配置
	 * @return
	 */
	@Bean
	public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		container.addMessageListener((message, bytes) -> {
			log.warn("接收到重新JVM 重新加载事件");
			DataCacheHolder.removeByKey(CacheConstants.DICT_DETAILS);
			// 发送刷新事件
			SpringContextHolder.publishEvent(new RefreshCacheEvent(this));
		}, new ChannelTopic(CacheConstants.DICT_DETAILS_REDIS_RELOAD_TOPIC));
		return container;
	}
}
