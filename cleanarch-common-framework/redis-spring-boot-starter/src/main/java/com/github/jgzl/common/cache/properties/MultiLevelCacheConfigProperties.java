package com.github.jgzl.common.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lihaifeng
 * @version 1.0.0
 * 全局配置
 */
@Data
@ConfigurationProperties(prefix = MultiLevelCacheConfigProperties.PREFIX)
public class MultiLevelCacheConfigProperties {
	public static final String PREFIX = "extend.cache.multi";
	private Set<String> cacheNames = new HashSet<>();
	/**
	 * 是否存储空值，默认true，防止缓存穿透
	 */
	private boolean cacheNullValues = true;
	/**
	 * 是否动态根据cacheName创建Cache的实现，默认true
	 */
	private boolean dynamic = true;
	/**
	 * 缓存key的前缀
	 */
	private String cachePrefix;
	private RedisConfigProp redis = new RedisConfigProp();
	private CaffeineConfigProp caffeine = new CaffeineConfigProp();
}
