package com.github.jgzl.common.cache.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

/**
 * @author lihaifeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = ExtendCacheConfigProperties.PREFIX)
public class ExtendCacheConfigProperties {

	public static final String PREFIX = "extend.cache";

	private boolean enabled;
	private RedisSwitch lock;
	private RedisSwitch limit;
	private RedisCacheSwitch cache;

	@Data
	public static class RedisSwitch {

		/**
		 * 是否启用
		 */
		private boolean enabled;
		/**
		 * 前缀
		 */
		private String prefix = "redis_plus_";
		/**
		 * 是否开启 @RedisLimit 的拦截器功能
		 */
		private boolean interceptor = true;
	}

	@Data
	public static class RedisCacheSwitch {
		/**
		 * 是否启用
		 */
		private boolean enabled;
		/**
		 * 前缀
		 */
		private String prefix = "redis_plus_cache_";

		/**
		 * 全局缓存时长,默认24小时
		 */
		private long timeout = 60 * 60 * 24;

		private List<RedisCacheItem> items;
	}

	/**
	 * 单项缓存配置信息
	 *
	 * @author lihaifeng
	 */
	@Data
	@EqualsAndHashCode(callSuper = false)
	public static class RedisCacheItem implements Serializable {
		/**
		 * 单项缓存名称
		 */
		private String name;

		/**
		 * 单项缓存存活时间
		 */
		private long timeout;

		/**
		 * 单项缓存是否开启
		 */
		private boolean enabled;
	}


}
