/*
 * Copyright (c) 2019-2019
 */

package com.github.jgzl.gateway.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

/**
 * @author lihaifeng
 */
@Slf4j
@RestController
@AllArgsConstructor
public class IndexHandler {

	private final RestTemplate restTemplate;

	/**
	 * http://localhost:8081/demo
	 * @return
	 */
	@RequestMapping("/token_key")
	public Mono<String> demo() {
		Object result = restTemplate.getForObject("http://localhost:8000/auth/oauth/token_key", Object.class);
		log.info("result:{}",result);
		return Mono.just("");
	}

	@RequestMapping("/ping")
	public Mono<String> ping(ServerHttpRequest serverHttpRequest) {
		log.info("ping_url:{}", serverHttpRequest.getURI());
		return Mono.just("pong");
	}

	@GetMapping("/")
	public Mono<String> index() {
		return Mono.just(desc());
	}

	private String desc() {
		StringBuilder sb = new StringBuilder(100);
		sb.append("<div style='color: blue'>Gateway has been started!</div>");
		sb.append("<br/>");
		sb.append("<div><ul><li>文档地址：<a href='doc.html'>doc.html</a></li></ul></div>");
		return sb.toString();
	}
}
