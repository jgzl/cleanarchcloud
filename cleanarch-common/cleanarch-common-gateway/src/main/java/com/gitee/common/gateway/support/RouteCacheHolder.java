package com.gitee.common.gateway.support;

import java.util.ArrayList;
import java.util.List;

import com.gitee.common.gateway.vo.RouteDefinitionVO;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import lombok.experimental.UtilityClass;

/**
 * @author lihaifeng
 * @date 2019-08-16
 * <p>
 * 路由缓存工具类
 */
@UtilityClass
public class RouteCacheHolder {
	private Cache<String, RouteDefinitionVO> cache = CacheUtil.newLFUCache(50);

	/**
	 * 获取缓存的全部对象
	 *
	 * @return routeList
	 */
	public List<RouteDefinitionVO> getRouteList() {
		List<RouteDefinitionVO> routeList = new ArrayList<>();
		cache.forEach(route -> routeList.add(route));
		return routeList;
	}

	/**
	 * 更新缓存
	 *
	 * @param routeList 缓存列表
	 */
	public void setRouteList(List<RouteDefinitionVO> routeList) {
		routeList.forEach(route -> cache.put(route.getId(), route));
	}

	/**
	 * 清空缓存
	 */
	public void removeRouteList() {
		cache.clear();
	}
}
