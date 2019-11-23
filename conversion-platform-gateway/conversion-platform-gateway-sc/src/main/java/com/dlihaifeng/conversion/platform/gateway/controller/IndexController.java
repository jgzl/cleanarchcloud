package com.dlihaifeng.conversion.platform.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Mono;

/**
 * @author lihaifeng
 */
@Controller
@ResponseBody
public class IndexController {
  @RequestMapping("ping")
  public Mono<String> ping() {
    return Mono.just("pong");
  }
}
