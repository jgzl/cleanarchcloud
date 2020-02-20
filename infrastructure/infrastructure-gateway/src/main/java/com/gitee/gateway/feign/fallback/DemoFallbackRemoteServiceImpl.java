package com.gitee.gateway.feign.fallback;

import com.gitee.gateway.feign.service.DemoRemoteService;

/**
 * @author lihaifeng
 */
public class DemoFallbackRemoteServiceImpl implements DemoRemoteService {
	@Override
	public String getServerPort() {
		return "数据降级";
	}
}
