//package com.github.jgzl.gateway.filter;
//
//import java.util.Arrays;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//import com.github.jgzl.gateway.exception.GateWayException;
//import com.github.jgzl.gateway.exception.SystemErrorType;
//import com.github.jgzl.gateway.model.TokenInfo;
//
//import lombok.extern.slf4j.Slf4j;
//import reactor.core.publisher.Mono;
//
//@Component
//@Slf4j
//public class AuthenticationFilter implements GlobalFilter, Ordered, InitializingBean {
//
//	private static Set<String> shouldSkipUrl = new LinkedHashSet<>();
//
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		//获取当前请求的路径
//		String reqPath = exchange.getRequest().getURI().getPath();
//
//		TokenInfo tokenInfo = exchange.getAttribute("tokenInfo");
//
//		//无需拦截直接放行
//		if (shouldSkipUrl.contains(reqPath)) {
//			return chain.filter(exchange);
//		}
//
//		if (!tokenInfo.isActive()) {
//			log.warn("token过期");
//			throw new GateWayException(SystemErrorType.TOKEN_TIMEOUT);
//		}
//
//		boolean hasPremisson = false;
//		//登陆用户的权限集合判断
//        List<String> authorities = Arrays.asList(tokenInfo.getAuthorities());
//        for (String url: authorities) {
//            if(reqPath.contains(url)) {
//                hasPremisson = true;
//                break;
//            }
//        }
//        if(!hasPremisson){
//            log.warn("权限不足");
//            throw new GateWayException(SystemErrorType.FORBIDDEN);
//        }
//
//
//		return chain.filter(exchange);
//	}
//
//	@Override
//	public int getOrder() {
//		return 2;
//	}
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		//模仿商品详情接口不需要认证
//		shouldSkipUrl.add("/product/selectProductInfoById");
//		//去认证的请求,本来就不需要拦截
//		shouldSkipUrl.add("/oauth/token");
//	}
//}
