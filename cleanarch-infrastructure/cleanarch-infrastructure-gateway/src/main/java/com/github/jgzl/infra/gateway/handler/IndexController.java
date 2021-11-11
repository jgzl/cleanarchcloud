package com.github.jgzl.infra.gateway.handler;

import com.github.jgzl.common.api.feign.upms.RemoteOauth2Service;
import com.github.jgzl.common.core.util.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author lihaifeng
 */
@Slf4j
@AllArgsConstructor
@RestController
public class IndexController {

	private RemoteOauth2Service remoteOauth2Service;

	/**
	 * http://localhost:8081/demo
	 * @return
	 */
	@GetMapping("/token_key")
	public Mono<Map<String,String>> demo() throws ExecutionException, InterruptedException, TimeoutException {
		CompletableFuture<Map<String,String>> future = CompletableFuture.supplyAsync(() -> {
			Map<String, String> result = remoteOauth2Service.token_key();
			return result;
		});
		return Mono.just(future.get());
	}

	@GetMapping("/heartbeat")
	public Mono<Result> heartbeat() {
		return Mono.just(Result.ok("成功接收心跳请求"));
	}

	@GetMapping("/")
	public Mono<Result> index() {
		return Mono.just(Result.success());
	}

}
