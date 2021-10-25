package com.github.jgzl.infra.upms.init;
import cn.hutool.core.collection.CollUtil;
import com.github.jgzl.infra.upms.dataobject.SysDict;
import com.github.jgzl.infra.upms.dataobject.SysDictItem;
import com.github.jgzl.infra.upms.service.SysDictItemService;
import com.github.jgzl.infra.upms.service.SysDictService;
import com.github.jgzl.common.cache.support.RefreshCacheEvent;
import com.github.jgzl.common.core.constant.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class CacheDataCommandLineRunner{

	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private SysDictItemService sysDictItemService;

	@Async
	@Order
	@EventListener({ WebServerInitializedEvent.class, RefreshCacheEvent.class })
	public void initRoute() {
		redisTemplate.delete(CacheConstants.DICT_DETAILS);
		log.info("开始初始化");

		List<SysDict> dictList = sysDictService.list();
		List<SysDictItem> dictItemList = sysDictItemService.list();
		if (CollUtil.isEmpty(dictList)) {
			dictList = CollUtil.newArrayList();
		}
		if (CollUtil.isEmpty(dictItemList)) {
			dictItemList = CollUtil.newArrayList();
		}

		// 通知网关重置路由
		redisTemplate.convertAndSend(CacheConstants.DICT_DETAILS_REDIS_RELOAD_TOPIC, "缓存更新");
		log.info("结束初始化");
	}
}
