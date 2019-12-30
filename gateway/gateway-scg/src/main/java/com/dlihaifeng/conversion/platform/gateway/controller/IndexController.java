/*
 * Copyright (c) 2019-2019
 */

package com.dlihaifeng.conversion.platform.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dlihaifeng.conversion.platform.gateway.feign.service.DemoRemoteService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author lihaifeng
 */
@Slf4j
@RestController
public class IndexController {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private DemoRemoteService demoRemoteService;

  /**
   * http://localhost:8081/demo
   * @return
   */
  @RequestMapping("/demo-rest")
  public Mono<String> demo() {
    String result = restTemplate.getForObject("http://application-demo/ping", String.class);
    return Mono.just(result);
  }

  @RequestMapping("ping")
  public Mono<String> ping(ServerHttpRequest serverHttpRequest) {
    log.info("ping_url:{}", serverHttpRequest.getURI());
    return Mono.just("pong");
  }

  /**
   * 使用openfeign调用demoservice相关服务
   * @param serverHttpRequest
   * @return
   */
  @RequestMapping("test")
  public Mono<String> test(ServerHttpRequest serverHttpRequest) {
    log.info("demo service port:{}", demoRemoteService.getServerPort());
    return Mono.just("demo service port:" + demoRemoteService.getServerPort());
  }
}
