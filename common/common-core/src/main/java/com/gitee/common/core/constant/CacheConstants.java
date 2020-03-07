package com.gitee.common.core.constant;

/**
 * @author lihaifeng
 * @date 2019-04-28
 * <p>
 * 缓存的key 常量
 */
public interface CacheConstants {

	/**
	 * 全局缓存，在缓存名称上加上该前缀表示该缓存不区分租户，比如:
	 * <p/>
	 * {@code @Cacheable(value = CacheConstants.GLOBALLY+CacheConstants.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result == null")}
	 */
	String GLOBALLY = "gl";

	/**
	 * 缓存分隔符
	 */
	String SPLIT = ":";

	/**
	 * 全局缓存公共前缀
	 */
	String PREFIX = GLOBALLY + SPLIT;

	/**
	 * 菜单信息缓存
	 */
	String MENU_DETAILS = "menu_details";

	/**
	 * 用户信息缓存
	 */
	String USER_DETAILS = "user_details";

	/**
	 * 字典信息缓存
	 */
	String DICT_DETAILS = "dict_details";


	/**
	 * oauth 客户端信息
	 */
	String CLIENT_DETAILS_KEY = "oauth:client:details";


	/**
	 * spring boot admin 事件key
	 */
	String EVENT_KEY = "event_key";

	/**
	 * 路由存放
	 */
	String ROUTE_KEY = "scg_gateway_route_key";

	/**
	 * redis reload 事件
	 */
	String ROUTE_REDIS_RELOAD_TOPIC = "scg_gateway_redis_route_reload_topic";

	String ZUUL_ROUTE_REDIS_RELOAD_TOPIC = "zuul_gateway_redis_route_reload_topic";

	/**
	 * 内存reload 时间
	 */
	String ROUTE_JVM_RELOAD_TOPIC = "scg_gateway_jvm_route_reload_topic";

	String ZUUL_ROUTE_JVM_RELOAD_TOPIC = "zuul_gateway_jvm_route_reload_topic";

	/**
	 * 参数缓存
	 */
	String PARAMS_DETAILS = "params_details";

	/**
	 * 租户缓存 (不区分租户)
	 */
	String TENANT_DETAILS = PREFIX + "tenant_details";

	/**
	 * Redis session 缓存前缀
	 */
	String REDIS_SESSION_PREFIX = PREFIX + "session";

	/**
	 * Redis token 缓存前缀
	 */
	String REDIS_TOKEN_PREFIX = PREFIX + "token" + SPLIT;

	/**
	 * Redis 客户端 缓存前缀
	 */
	String REDIS_CLIENTS_PREFIX = PREFIX + "clients" + SPLIT;

	/**
	 * Redis 客户端 缓存前缀
	 */
	String REDIS_USER_PREFIX = PREFIX + "user" + SPLIT;

	/**
	 * 验证码缓存key
	 */
	String DEFAULT_CODE_KEY = PREFIX + "code" + SPLIT;

	/**
	 * 默认过期时间 60秒
	 */
	int DEFAULT_EXPIRE_SECONDS = 60;
}
