package com.github.jgzl.gateway.config;

import com.github.jgzl.common.core.constant.GatewayConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 修复官方knife4j不支持动态网关，无法渲染正常聚合文档
 * @author lihaifeng
 */
@Slf4j
@Primary
@Component
@AllArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

	private final RouteDefinitionLocator routeDefinitionLocator;

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		routeDefinitionLocator.getRouteDefinitions().subscribe(routeDefinition -> {
			routeDefinition.getPredicates().stream()
					.filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
					.forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
							predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
									.replace("/**", GatewayConstants.SWAGGER_URL))));
		});
		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location) {
		log.info("name:{},location:{}", name, location);
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion(GatewayConstants.SWAGGER_VERSION);
		return swaggerResource;
	}
}
