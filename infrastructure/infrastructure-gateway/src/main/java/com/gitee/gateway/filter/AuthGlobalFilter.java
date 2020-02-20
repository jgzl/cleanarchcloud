/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gitee.gateway.filter;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.gitee.gateway.utils.GatewayConstants;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 自定义网关过滤器
 * @author lihaifeng
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

	private StringRedisTemplate redisTemplate;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Welcome to AuthGlobalFilter.");
		ServerHttpRequest request = exchange.getRequest();
		String path = request.getURI().getPath();
		if (path.startsWith("/")) {
			log.info("访问认证授权模块不需要token");
			return chain.filter(exchange);
		}
		HttpHeaders headers = request.getHeaders();
		List<String> authorizations = headers.get(GatewayConstants.AUTHORIZATION);
		if (authorizations != null) {
			String token = authorizations.get(0);
			boolean startsWith = token.startsWith(GatewayConstants.BEARER_SPACE);
			if (startsWith) {
				token = token.substring(7);
				redisTemplate.setKeySerializer(new StringRedisSerializer());
				redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
				boolean flag = Optional.ofNullable(redisTemplate.opsForValue().get("platform_oauth:access:" + token))
						.isPresent();
				if (flag) {
					log.info("redis中有这个token，则此token未失效");
					return chain.filter(exchange);
				}
			}
		}
		log.info("redis中没有这个token，则此token失效");
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		return response.setComplete();
	}

	@Override
	public int getOrder() {
		return 2;
	}
}
