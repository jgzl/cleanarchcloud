package com.gitee.swagger.config;

import com.gitee.swagger.support.SwaggerResourceHandler;
import com.gitee.swagger.support.SwaggerSecurityHandler;
import com.gitee.swagger.support.SwaggerUiHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * @author lengleng
 * @date 2020/10/2
 * <p>
 * 网关swagger 配置类，仅在webflux 环境生效哦
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ComponentScan("com.gitee.swagger.support")
public class GatewaySwaggerAutoConfiguration {

	private final SwaggerResourceHandler swaggerResourceHandler;

	private final SwaggerSecurityHandler swaggerSecurityHandler;

	private final SwaggerUiHandler swaggerUiHandler;

	@Bean
	public WebFluxSwaggerConfiguration fluxSwaggerConfiguration() {
		return new com.gitee.swagger.config.WebFluxSwaggerConfiguration();
	}

	@Bean
	public RouterFunction swaggerRouterFunction() {
		return RouterFunctions
				.route(RequestPredicates.GET("/swagger-resources").and(RequestPredicates.accept(MediaType.ALL)),
						swaggerResourceHandler)
				.andRoute(RequestPredicates.GET("/swagger-resources/configuration/ui")
						.and(RequestPredicates.accept(MediaType.ALL)), swaggerUiHandler)
				.andRoute(RequestPredicates.GET("/swagger-resources/configuration/security")
						.and(RequestPredicates.accept(MediaType.ALL)), swaggerSecurityHandler);
	}

}
