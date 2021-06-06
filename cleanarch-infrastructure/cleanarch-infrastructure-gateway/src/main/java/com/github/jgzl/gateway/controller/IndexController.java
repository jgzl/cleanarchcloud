/*
 * Copyright (c) 2019-2019
 */

package com.github.jgzl.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

	/**
	 * http://localhost:8081/demo
	 * @return
	 */
	@RequestMapping("/oauth/tokenkey")
	public Mono<String> demo() {
		Object result = restTemplate.getForObject("http://cleanarch-business-auth/oauth/token_key", Object.class);
		log.info("result:{}",result);
		return Mono.just("");
	}

	@RequestMapping("ping")
	public Mono<String> ping(ServerHttpRequest serverHttpRequest) {
		log.info("ping_url:{}", serverHttpRequest.getURI());
		return Mono.just("pong");
	}
}
