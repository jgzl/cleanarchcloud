package com.dlihaifeng.conversion.platform.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import reactor.core.publisher.Mono;

/**
 * @author lihaifeng
 */
@Controller
public class IndexController {
  @RequestMapping("ping")
  public Mono<String> ping() {
    return Mono.just("pong");
  }
}
