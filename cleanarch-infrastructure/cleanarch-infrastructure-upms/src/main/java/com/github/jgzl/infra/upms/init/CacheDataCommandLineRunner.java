package com.github.jgzl.infra.upms.init;

import com.github.jgzl.common.cache.support.RefreshCacheEvent;
import com.github.jgzl.common.core.constant.CacheConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheDataCommandLineRunner{

	private final RedisTemplate<String,String> redisTemplate;

	@Async
	@Order
	@EventListener({ WebServerInitializedEvent.class, RefreshCacheEvent.class })
	public void initRoute() {
		log.info("========================开始缓存预热========================");
		// 通知数据进行重置
		redisTemplate.convertAndSend(CacheConstants.DICT_DETAILS_REDIS_RELOAD_TOPIC, "缓存更新");
		log.info("========================结束缓存预热========================");
	}
}
