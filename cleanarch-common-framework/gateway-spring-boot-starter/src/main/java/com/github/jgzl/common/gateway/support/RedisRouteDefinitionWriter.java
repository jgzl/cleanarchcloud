package com.github.jgzl.common.gateway.support;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.gateway.vo.RouteDefinitionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lihaifeng
 * @date 2018/10/31
 * <p>
 * redis 保存路由信息，优先级比配置文件高
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisRouteDefinitionWriter implements RouteDefinitionRepository {

	private final RedissonClient redisson;

	@Override
	public Mono<Void> save(Mono<RouteDefinition> route) {
		return route.flatMap(r -> {
			RouteDefinitionVo vo = new RouteDefinitionVo();
			BeanUtils.copyProperties(r, vo);
			log.info("保存路由信息{}", vo);
			redisson.getMap(CacheConstants.ROUTE_KEY).put(r.getId(), JSONUtil.toJsonStr(vo));
			redisson.getTopic(CacheConstants.ROUTE_JVM_RELOAD_TOPIC).publish("新增路由信息,网关缓存更新");
			return Mono.empty();
		});
	}

	@Override
	public Mono<Void> delete(Mono<String> routeId) {
		routeId.subscribe(id -> {
			log.info("删除路由信息{}", id);
			redisson.getMap(CacheConstants.ROUTE_KEY).remove(id);
		});
		redisson.getTopic(CacheConstants.ROUTE_JVM_RELOAD_TOPIC).publish("删除路由信息,网关缓存更新");
		return Mono.empty();
	}

	/**
	 * 动态路由入口
	 * <p>
	 * 1. 先从内存中获取 2. 为空加载Redis中数据 3. 更新内存
	 *
	 * @return
	 */
	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		List<RouteDefinitionVo> routeList = RouteCacheHolder.getRouteList();
		if (CollUtil.isNotEmpty(routeList)) {
			log.debug("内存 中路由定义条数： {}， {}", routeList.size(), routeList);
			return Flux.fromIterable(routeList);
		}

		Collection<String> values = redisson.<String, String>getMap(CacheConstants.ROUTE_KEY).values();
		log.debug("redis 中路由定义条数： {}， {}", values.size(), values);
		List<RouteDefinitionVo> routeDefinitionList = values.stream().map(value -> JSONUtil.toBean(value, RouteDefinitionVo.class)).collect(Collectors.toList());
		RouteCacheHolder.setRouteList(routeDefinitionList);
		return Flux.fromIterable(routeDefinitionList);
	}

}
