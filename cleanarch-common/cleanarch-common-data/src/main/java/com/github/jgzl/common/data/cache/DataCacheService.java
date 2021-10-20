package com.github.jgzl.common.data.cache;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.data.mybatis.DicItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据字典自动绑定
 */
@Slf4j
@Component
public class DataCacheService {

	public static Map<String, Map<String,String>> dicMap = MapUtil.newConcurrentHashMap();

	private final RedisTemplate<String,String> redisTemplate;

	public DataCacheService(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Async
	@PostConstruct
	@EventListener(value = RefreshCacheEvent.class)
	public void initDic() {
		Map<String, Map<String, String>> dictDetailsMap = DataCacheHolder.getValue(CacheConstants.DICT_DETAILS);
		if (CollUtil.isNotEmpty(dictDetailsMap)) {
			log.debug("内存中数据字典key数,{},{}",dictDetailsMap.keySet().size(),dictDetailsMap);
			dicMap.clear();
			for (String key : dictDetailsMap.keySet()) {
				dicMap.put(key,dictDetailsMap.get(key));
			}
		}else {
			BoundHashOperations<String, String, String> boundHashOps = redisTemplate.boundHashOps(CacheConstants.DICT_DETAILS);
			Set<String> keys = boundHashOps.keys();
			if (keys!=null&&keys.size()>0) {
				log.debug("内存中数据字典key数,{},{}",keys.size(),keys);
				for (String key : keys) {
					String value = boundHashOps.get(key);
					List<DicItem> dicItems = JSONUtil.toList(value, DicItem.class);
					Map<String,String> dicItemMap = new HashMap<>();
					dicMap.put(key,dicItemMap);
					dictDetailsMap.put(key,dicItemMap);
					for (DicItem dicItem : dicItems) {
						dicItemMap.put(dicItem.getItemKey(),dicItem.getItemName());
					}
				}
				DataCacheHolder.setValue(CacheConstants.DICT_DETAILS,dictDetailsMap);
			}
		}
	}
}
