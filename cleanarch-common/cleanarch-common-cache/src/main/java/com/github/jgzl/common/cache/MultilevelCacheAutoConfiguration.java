package com.github.jgzl.common.cache;
import com.github.jgzl.common.cache.properties.CacheConfigProperties;
import com.github.jgzl.common.cache.support.CacheMessageListener;
import com.github.jgzl.common.cache.support.CustomRedisRepository;
import com.github.jgzl.common.cache.support.RedisCaffeineCacheManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author fuwei.deng
 * @version 1.0.0
 */

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(name = { "org.redisson.spring.starter.RedissonAutoConfiguration",
		"org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration" })
@EnableConfigurationProperties(CacheConfigProperties.class)
public class MultilevelCacheAutoConfiguration {

	@Bean
	@ConditionalOnBean(RedisConnectionFactory.class)
	public RedisTemplate<Object, Object> stringKeyRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	@Bean
	@ConditionalOnBean(RedisTemplate.class)
	public RedisCaffeineCacheManager cacheManager(
			CacheConfigProperties cacheConfigProperties,
			RedisTemplate<Object, Object> redisTemplate) {
		return new RedisCaffeineCacheManager(cacheConfigProperties, redisTemplate);
	}

	@Bean
	@ConditionalOnBean(RedisConnectionFactory.class)
	public RedisMessageListenerContainer redisMessageListenerContainer(
			CacheConfigProperties cacheConfigProperties,
			RedisConnectionFactory redisConnectionFactory,
			RedisTemplate<Object, Object> stringKeyRedisTemplate,
			RedisCaffeineCacheManager redisCaffeineCacheManager) {
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
		CacheMessageListener cacheMessageListener = new CacheMessageListener(stringKeyRedisTemplate,
				redisCaffeineCacheManager);
		redisMessageListenerContainer.addMessageListener(cacheMessageListener,
				new ChannelTopic(cacheConfigProperties.getRedis().getTopic()));
		return redisMessageListenerContainer;
	}

	/**
	 * redis自定义操作仓库
	 * @param stringRedisTemplate
	 * @return
	 */
	@Bean
	public CustomRedisRepository customRedisRepository(RedisTemplate stringRedisTemplate){
		return new CustomRedisRepository(stringRedisTemplate);
	}
}
