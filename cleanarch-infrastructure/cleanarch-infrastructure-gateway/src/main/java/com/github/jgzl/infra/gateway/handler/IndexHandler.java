package com.github.jgzl.infra.gateway.handler;

import com.github.jgzl.common.api.feign.RemoteOauth2Service;
import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.log.annotation.SysLog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author lihaifeng
 */
@Slf4j
@RestController
@AllArgsConstructor
public class IndexHandler {

	private WebClient.Builder webClientBuilder;

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
		return Mono.just(Result.ok(null,"成功接收心跳请求"));
	}

	@GetMapping("/")
	public Mono<Result> index() {
		return Mono.just(Result.ok());
	}

}
