package com.github.jgzl.infra.upms.config;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.gateway.support.DynamicRouteInitEvent;
import com.github.jgzl.common.gateway.vo.RouteDefinitionVo;
import com.github.jgzl.infra.upms.service.SysRouteConfService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.annotation.Async;

import java.net.URI;
import java.util.Map;

/**
 * @author lihaifeng
 * @date 2018/10/31
 * <p>
 * 容器启动后保存配置文件里面的路由信息到Redis
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class DynamicRouteInitRunner {

	private final RedissonClient redisson;

	private final SysRouteConfService routeConfService;

	@Async
	@Order
	@EventListener({ WebServerInitializedEvent.class, DynamicRouteInitEvent.class })
	public void initRoute() {
		redisson.getKeys().delete(CacheConstants.ROUTE_KEY);
		log.info("开始初始化网关路由");

		routeConfService.list().forEach(route -> {
			RouteDefinitionVo vo = new RouteDefinitionVo();
			vo.setRouteName(route.getRouteName());
			vo.setId(route.getRouteId());
			vo.setUri(URI.create(route.getUri()));
			vo.setOrder(route.getOrder());

			JSONArray filterObj = JSONUtil.parseArray(route.getFilters());
			vo.setFilters(filterObj.toList(FilterDefinition.class));
			JSONArray predicateObj = JSONUtil.parseArray(route.getPredicates());
			vo.setPredicates(predicateObj.toList(PredicateDefinition.class));
			vo.setMetadata(JSONUtil.toBean(route.getMetadata(), Map.class));
			log.info("加载路由ID：{},{}", route.getRouteId(), vo);
			redisson.getMap(CacheConstants.ROUTE_KEY).put(route.getRouteId(), JSONUtil.toJsonStr(vo));
		});

		// 通知网关重置路由
		redisson.getTopic(CacheConstants.ROUTE_JVM_RELOAD_TOPIC).publish("路由信息,网关缓存更新");
		log.debug("初始化网关路由结束 ");
	}

	/**
	 * redis 监听配置,监听 gateway_redis_route_reload_topic,重新加载Redis
	 * @param redisConnectionFactory redis 配置
	 * @return
	 */
	@Bean
	public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		container.addMessageListener((message, bytes) -> {
			log.warn("接收到重新Redis 重新加载路由事件");
			initRoute();
		}, new ChannelTopic(CacheConstants.ROUTE_REDIS_RELOAD_TOPIC));
		return container;
	}

}
