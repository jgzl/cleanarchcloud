package com.github.jgzl.infra.gateway.handler;

import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.core.util.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author lihaifeng
 */
@Slf4j
@RestController
@AllArgsConstructor
public class IndexHandler {

	private WebClient.Builder webClientBuilder;
	/**
	 * http://localhost:8081/demo
	 * @return
	 */
	@GetMapping("/token_key")
	public Mono<Result> demo() {
		return webClientBuilder.build()
				.get()
				.uri(String.format("http://%s/oauth/token_key", ServiceNameConstants.UPMS_SERVICE))
				.retrieve()
				.bodyToMono(Result.class);
	}

	@GetMapping("/health")
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

	public static void main(String[] args) {
		String format = String.format("http://%s/oauth/token_key", ServiceNameConstants.UPMS_SERVICE);
		log.info(format);
	}
}
