package com.github.jgzl.common.datasource;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.github.jgzl.common.datasource.config.ExtendDataSourceProperties;
import com.github.jgzl.common.datasource.config.JdbcDynamicDataSourceProvider;
import com.github.jgzl.common.datasource.config.LastParamDsProcessor;
import com.github.jgzl.common.datasource.config.WebMvcConfig;
import lombok.AllArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lihaifeng
 * @date 2020-02-06
 * <p>
 * 动态数据源切换配置
 */
@Configuration
@AllArgsConstructor
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(ExtendDataSourceProperties.class)
public class DynamicDataSourceAutoConfiguration {

	private final StringEncryptor stringEncryptor;

	private final ExtendDataSourceProperties properties;

	@Bean
	public DynamicDataSourceProvider dynamicDataSourceProvider() {
		return new JdbcDynamicDataSourceProvider(stringEncryptor, properties);
	}

	@Bean
	public DsProcessor dsProcessor() {
		return new LastParamDsProcessor();
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfig();
	}

}
