package com.dlihaifeng.conversion.platform.gateway.feign.fallback;

import com.dlihaifeng.conversion.platform.gateway.feign.service.DemoRemoteService;

/**
 * @author lihaifeng
 */
public class DemoFallbackRemoteServiceImpl implements DemoRemoteService {
  @Override
  public String getServerPort() {
    return "数据降级";
  }
}
