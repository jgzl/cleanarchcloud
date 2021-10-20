package com.github.jgzl.common.data.cache;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class DataCacheHolder {

	private Cache<String, Map<String,Map<String,String>>> cache = CacheUtil.newLFUCache(10);
	
	public Map<String,Map<String,String>> getValue(String key){
		return cache.get(key);
	}

	public void setValue(String key,Map<String,Map<String,String>> value){
		cache.put(key,value);
	}

	public void removeByKey(String key){
		cache.remove(key);
	}

}
