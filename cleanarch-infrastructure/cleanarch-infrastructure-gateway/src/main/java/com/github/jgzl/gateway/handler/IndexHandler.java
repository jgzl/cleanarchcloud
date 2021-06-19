/*
 * Copyright (c) 2019-2019
 */

package com.github.jgzl.gateway.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author lihaifeng
 */
@Slf4j
@RestController
@AllArgsConstructor
public class IndexHandler {

//	private CodeGenFeignClient codeGenFeignClient;

	/**
	 * http://localhost:8081/demo
	 * @return
	 */
	@GetMapping("/test/codegen")
	public Mono demo() {
//		Result<String> ok = codeGenFeignClient.ok();
		return Mono.just("ok");
	}

	@GetMapping("/test/ping")
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
