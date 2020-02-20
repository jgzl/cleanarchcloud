package com.gitee.gateway.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.gitee.gateway.feign.fallback.DemoFallbackRemoteServiceImpl;

/**
 * @author lihaifeng
 */
@FeignClient(name = "application-demo", fallback = DemoFallbackRemoteServiceImpl.class)
public interface DemoRemoteService {
  @GetMapping("/ping")
  String getServerPort();
}
