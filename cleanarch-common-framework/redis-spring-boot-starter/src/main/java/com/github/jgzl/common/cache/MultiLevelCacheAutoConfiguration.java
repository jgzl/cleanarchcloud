package com.github.jgzl.common.cache;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import com.github.jgzl.common.cache.properties.MultiLevelCacheConfigProperties;
import com.github.jgzl.common.cache.support.CacheMessageListener;
import com.github.jgzl.common.cache.support.CustomRedisRepository;
import com.github.jgzl.common.cache.support.RedisCaffeineCacheManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author fuwei.deng
 * @version 1.0.0
 */

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(name = { "org.redisson.spring.starter.RedissonAutoConfiguration",
		"org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration" })
@EnableConfigurationProperties(MultiLevelCacheConfigProperties.class)
public class MultiLevelCacheAutoConfiguration extends CachingConfigurerSupport {

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
			MultiLevelCacheConfigProperties multiLevelCacheConfigProperties,
			RedisTemplate<Object, Object> redisTemplate) {
		return new RedisCaffeineCacheManager(multiLevelCacheConfigProperties, redisTemplate);
	}

	@Bean
	@ConditionalOnBean(RedisConnectionFactory.class)
	@ConditionalOnMissingBean
	public RedisMessageListenerContainer redisMessageListenerContainer(
			MultiLevelCacheConfigProperties multiLevelCacheConfigProperties,
			RedisConnectionFactory redisConnectionFactory,
			RedisTemplate<Object, Object> stringKeyRedisTemplate,
			RedisCaffeineCacheManager redisCaffeineCacheManager) {
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
		CacheMessageListener cacheMessageListener = new CacheMessageListener(stringKeyRedisTemplate,
				redisCaffeineCacheManager);
		redisMessageListenerContainer.addMessageListener(cacheMessageListener,
				new ChannelTopic(multiLevelCacheConfigProperties.getRedis().getTopic()));
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


	/**
	 * 自定义SpringCache缓存key
	 */
	@Primary
	@Bean("keyGenerator")
	@ConditionalOnMissingBean(KeyGenerator.class)
	@Override
	public KeyGenerator keyGenerator() {
		return (target, method, params) -> {
			//保证key有序
			Map<String, Object> keyMap = new LinkedHashMap<>();
			//放入target的名字
			keyMap.put("target", target.getClass().toGenericString());
			//放入method的名字
			keyMap.put("method", method.getName());
			if (ArrayUtil.isNotEmpty(params)) {
				//把所有参数放进去
				for (int i = 0; i < params.length; i++) {
					keyMap.put("params-" + i, params[i]);
				}
			}
			final String jsonText = JSONUtil.toJsonStr(keyMap);
			//使用MD5生成位移key
			return MD5.create().digestHex(jsonText);
		};
	}
}
