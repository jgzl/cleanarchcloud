package com.github.jgzl.common.cache;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import com.github.jgzl.common.cache.properties.MultiLevelCacheConfigProperties;
import com.github.jgzl.common.cache.support.CacheMessage;
import com.github.jgzl.common.cache.support.RedisCaffeineCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lihaifeng
 * @version 1.0.0
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(name = { "org.redisson.spring.starter.RedissonAutoConfiguration",
		"org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration" })
@EnableConfigurationProperties(MultiLevelCacheConfigProperties.class)
public class MultiLevelCacheAutoConfiguration extends CachingConfigurerSupport {

	@Bean
	public RedisCaffeineCacheManager cacheManager(
			MultiLevelCacheConfigProperties multiLevelCacheConfigProperties,
			RedissonClient redisson) {
		return new RedisCaffeineCacheManager(multiLevelCacheConfigProperties, redisson);
	}

	@Bean(value="multiLevelCacheTopic")
	public RTopic topic(
			RedissonClient redisson,
			RedisCaffeineCacheManager redisCaffeineCacheManager,
			MultiLevelCacheConfigProperties multiLevelCacheConfigProperties
	) {
		RTopic topic = redisson.getTopic(multiLevelCacheConfigProperties.getRedis().getTopic());
		topic.addListener(CacheMessage.class, new MessageListener<CacheMessage>() {
			@Override
			public void onMessage(CharSequence channel, CacheMessage cacheMessage) {
				log.debug("receive a redis topic message, clear local cache, the cacheName is {}, the key is {}",
						cacheMessage.getCacheName(), cacheMessage.getKey());
				redisCaffeineCacheManager.clearLocal(cacheMessage.getCacheName(), cacheMessage.getKey());
			}
		});
		return topic;
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
