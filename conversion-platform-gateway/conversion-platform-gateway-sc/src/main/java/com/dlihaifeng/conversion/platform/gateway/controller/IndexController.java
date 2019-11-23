/*
 * Copyright (c) 2019-2019
 */

package com.dlihaifeng.conversion.platform.gateway.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author lihaifeng
 */
@Slf4j
@Controller
@ResponseBody
public class IndexController {

  @RequestMapping("ping")
  public Mono<String> ping(ServerHttpRequest serverHttpRequest) {
    log.info("serverRequest:{}", serverHttpRequest);
    return Mono.just("pong");
  }
}
