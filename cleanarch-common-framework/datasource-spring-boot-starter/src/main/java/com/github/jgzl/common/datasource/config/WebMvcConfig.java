package com.github.jgzl.common.datasource.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lihaifeng
 * @date 2020/12/11
 */
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ClearTtlDsInterceptor()).addPathPatterns("/**");
	}

}