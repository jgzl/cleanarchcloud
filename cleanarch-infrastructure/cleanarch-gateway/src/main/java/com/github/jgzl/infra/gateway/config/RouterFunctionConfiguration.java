package com.github.jgzl.infra.gateway.config;

import com.github.jgzl.infra.gateway.handler.ImageCodeCheckHandler;
import com.github.jgzl.infra.gateway.handler.ImageCodeCreateHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * @author lihaifeng
 * @date 2018/7/5 路由配置信息
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class RouterFunctionConfiguration {

	private final ImageCodeCheckHandler imageCodeCheckHandler;

	private final ImageCodeCreateHandler imageCodeCreateHandler;

	@Bean
	public RouterFunction routerFunction() {
		return RouterFunctions
				.route(RequestPredicates.path("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
						imageCodeCreateHandler)
				.andRoute(RequestPredicates.POST("/code/check").and(RequestPredicates.accept(MediaType.ALL)),
						imageCodeCheckHandler);

	}

}
