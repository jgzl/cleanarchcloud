package com.github.jgzl.common.gateway.configuration;

import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.util.SpringContextHolder;
import com.github.jgzl.common.gateway.exception.RouteCheckException;
import com.github.jgzl.common.gateway.support.DynamicRouteHealthIndicator;
import com.github.jgzl.common.gateway.support.RouteCacheHolder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author lihaifeng
 * @date 2018/11/5
 * <p>
 * 动态路由配置类
 */
@Slf4j
@Configuration
@ComponentScan("com.github.jgzl.common.gateway")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class DynamicRouteAutoConfiguration {

	/**
	 * 配置文件设置为空 redis 加载为准
	 *
	 * @return
	 */
	@Bean
	public PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator() {
		return new PropertiesRouteDefinitionLocator(new GatewayProperties());
	}

	/**
	 * redis 监听配置
	 *
	 * @param redisson redis 配置
	 * @return
	 */
	@Bean(value = "routeJvmReloadTopic")
	public RTopic redisContainer(RedissonClient redisson) {
		RTopic topic = redisson.getTopic(CacheConstants.ROUTE_JVM_RELOAD_TOPIC);
		topic.addListener(String.class, new MessageListener<String>() {
			@Override
			public void onMessage(CharSequence channel, String msg) {
				log.warn("接收到重新JVM 重新加载路由事件");
				RouteCacheHolder.removeRouteList();
				// 发送刷新路由事件
				SpringContextHolder.publishEvent(new RefreshRoutesEvent(this));
			}
		});
		return topic;
	}

	/**
	 * 动态路由监控检查
	 *
	 * @param redisson
	 * @return
	 */
	@Bean
	public DynamicRouteHealthIndicator healthIndicator(RedissonClient redisson) {
		if (redisson.getKeys().countExists(CacheConstants.ROUTE_KEY) == 0) {
			throw new RouteCheckException("路由信息未初始化，网关启动失败");
		}

		return new DynamicRouteHealthIndicator(redisson);
	}

}
