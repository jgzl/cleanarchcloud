//package com.github.jgzl.gateway.filter;
//
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.server.ServerWebExchange;
//
//import com.github.jgzl.gateway.exception.GateWayException;
//import com.github.jgzl.gateway.exception.SystemErrorType;
//import com.github.jgzl.gateway.model.TokenInfo;
//import com.github.jgzl.gateway.utils.GatewayConstants;
//
//import lombok.extern.slf4j.Slf4j;
//import reactor.core.publisher.Mono;
//
//@Component
//@Slf4j
//public class AuthorizationFilter implements GlobalFilter, Ordered, InitializingBean {
//
//	@Autowired
//	private RestTemplate restTemplate;
//
//	private static Set<String> shouldSkipUrl = new LinkedHashSet<>();
//
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//		String reqPath = exchange.getRequest().getURI().getPath();
//		log.info("网关认证开始URL->:{}", reqPath);
//
//		//1:不需要认证的url
//		if (shouldSkip(reqPath)) {
//			log.info("无需认证的路径");
//			return chain.filter(exchange);
//		}
//
//		//获取请求头
//		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//
//		//请求头为空
//		if (StringUtils.isEmpty(authHeader)) {
//			log.warn("需要认证的url,请求头为空");
//			throw new GateWayException(SystemErrorType.UNAUTHORIZED_HEADER_IS_EMPTY);
//		}
//
//		TokenInfo tokenInfo = null;
//		try {
//			//获取token信息
//			tokenInfo = getTokenInfo(authHeader);
//		} catch (Exception e) {
//			log.warn("校验令牌异常:{}", authHeader);
//			throw new GateWayException(SystemErrorType.INVALID_TOKEN);
//		}
//
//		//向headers中放文件，记得build
//		ServerHttpRequest request = exchange.getRequest().mutate().header("userName", tokenInfo.getUser_name()).build();
//		//将现在的request 变成 change对象
//		ServerWebExchange serverWebExchange = exchange.mutate().request(request).build();
//
//		serverWebExchange.getAttributes().put("tokenInfo", tokenInfo);
//
//		return chain.filter(serverWebExchange);
//	}
//
//	private TokenInfo getTokenInfo(String authHeader) {
//		String token = StringUtils.substringAfter(authHeader, GatewayConstants.BEARER_SPACE);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		headers.setBasicAuth(GatewayConstants.clientId, GatewayConstants.clientSecret);
//
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("token", token);
//
//		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
//
//		ResponseEntity<TokenInfo> response = restTemplate
//				.exchange(GatewayConstants.checkTokenUrl, HttpMethod.POST, entity, TokenInfo.class);
//
//		log.info("token info :" + response.getBody().toString());
//
//		return response.getBody();
//	}
//
//	private boolean shouldSkip(String reqPath) {
//
//		for (String skipPath : shouldSkipUrl) {
//			if (reqPath.contains(skipPath)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//
//	@Override
//	public int getOrder() {
//		return 1;
//	}
//
//	@Override
//	public void afterPropertiesSet() {
//		//去认证的请求,本来就不需要拦截
//		shouldSkipUrl.add("/oauth/token");
//	}
//}
