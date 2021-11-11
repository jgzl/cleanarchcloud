package com.github.jgzl.infra.gateway.handler;

import com.github.jgzl.common.core.util.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author lihaifeng
 */
@Slf4j
@AllArgsConstructor
@RestController
public class IndexController {

	@GetMapping("/heartbeat")
	public Mono<Result> heartbeat() {
		return Mono.just(Result.ok("成功接收心跳请求"));
	}

	@GetMapping("/")
	public Mono<Result> index() {
		return Mono.just(Result.success());
	}

}
