package com.github.jgzl.common.gateway.support;

import com.github.jgzl.common.core.constant.CacheConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * @author lihaifeng
 * @date 2020/11/19
 * <p>
 * 动态路由检查检查
 */
@Slf4j
@RequiredArgsConstructor
public class DynamicRouteHealthIndicator extends AbstractHealthIndicator {

	private final RedissonClient redisson;

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		if (redisson.getKeys().countExists(CacheConstants.ROUTE_KEY) > 0) {
			builder.up();
		} else {
			log.warn("动态路由监控检查失败，当前无路由配置");
			builder.down();
		}
	}

}
