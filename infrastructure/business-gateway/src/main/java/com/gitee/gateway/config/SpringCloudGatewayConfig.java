/*
 * Copyright (c) 2019-2019
 */

package com.gitee.gateway.config;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.DefaultBlockRequestHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;

import reactor.core.publisher.Mono;

/**
 * @author lihaifeng
 */
@Configuration
public class SpringCloudGatewayConfig {
	/**
	 * ribbon负载均衡
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public BlockRequestHandler blockRequestHandler() {
		return (exchange, t) -> {
			if (t instanceof DegradeException) {
				return ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON)
						.body(fromValue("{\"code\":\"500\",\"msg\":\"触发降级参数规则\"}"));
			}
			if (t instanceof FlowException) {
				return ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON)
						.body(fromValue("{\"code\":\"500\",\"msg\":\"触发流控参数规则\"}"));
			}
			if (t instanceof SystemBlockException) {
				return ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON)
						.body(fromValue("{\"code\":\"500\",\"msg\":\"触发系统参数规则\"}"));
			}
			if (t instanceof ParamFlowException) {
				return ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON)
						.body(fromValue("{\"code\":\"500\",\"msg\":\"触发热点参数规则\"}"));
			}
			if (t instanceof AuthorityException) {
				return ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON)
						.body(fromValue("{\"code\":\"500\",\"msg\":\"触发授权参数规则\"}"));
			}
			return ServerResponse.status(500).contentType(MediaType.APPLICATION_JSON)
					.body(fromValue("{\"code\":\"500\",\"msg\":\"业务异常\"}"));
		};
	}
}
