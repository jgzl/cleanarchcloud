package com.github.jgzl.common.gateway.rule;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author lihaifeng
 * @date 2020/1/12
 * <p>
 * 灰度路由
 */
public interface GrayLoadBalancer {

	/**
	 * 根据serviceId 筛选可用服务
	 *
	 * @param serviceId 服务ID
	 * @param request   当前请求
	 * @return
	 */
	ServiceInstance choose(String serviceId, ServerHttpRequest request);

}
