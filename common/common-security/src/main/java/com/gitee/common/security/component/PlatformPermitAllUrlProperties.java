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

package com.gitee.common.security.component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 * @date 2019/4/13
 * <p>
 * 资源服务器对外直接暴露URL,如果设置contex-path 要特殊处理
 */
@Slf4j
@Configuration
@ConditionalOnExpression("!'${security.oauth2.client.ignore-urls}'.isEmpty()")
@ConfigurationProperties(prefix = "security.oauth2.client")
public class PlatformPermitAllUrlProperties implements InitializingBean {
	private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

	@Autowired
	private WebApplicationContext applicationContext;

	@Getter
	@Setter
	private List<String> ignoreUrls = new ArrayList<>();

	@Override
	public void afterPropertiesSet() {
		log.debug("PlatformPermitAllUrlProperties----------------------------------->start");
/*		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
		ignoreUrls.forEach(url->
				log.info("PlatformPermitAllUrlProperties-ignoreUrl:{}",url)
		);
		map.keySet().forEach(info -> {
			HandlerMethod handlerMethod = map.get(info);
			log.info("PlatformPermitAllUrlProperties-handlerMethod:{}",handlerMethod);
			// 获取方法上边的注解 替代path variable 为 *
			Inner method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Inner.class);
			Optional.ofNullable(method)
					.ifPresent(inner -> info.getPatternsCondition().getPatterns()
							.forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, StringPool.ASTERISK))));

			// 获取类上边的注解, 替代path variable 为 *
			Inner controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Inner.class);
			Optional.ofNullable(controller)
					.ifPresent(inner -> info.getPatternsCondition().getPatterns()
							.forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, StringPool.ASTERISK))));
		});*/

	}
}
