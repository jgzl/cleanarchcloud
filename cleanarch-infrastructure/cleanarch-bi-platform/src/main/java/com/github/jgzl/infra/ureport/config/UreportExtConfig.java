package com.github.jgzl.infra.ureport.config;

import com.bstek.ureport.UReportPropertyPlaceholderConfigurer;
import com.bstek.ureport.provider.report.ReportProvider;
import com.github.jgzl.common.oss.OssProperties;
import com.github.jgzl.common.oss.service.OssTemplate;
import com.github.jgzl.infra.ureport.processor.UReportPropertyPlaceholderConfigurerPlus;
import com.github.jgzl.infra.ureport.provider.DfsReportProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * @author lihaifeng
 * @date 2020/7/25
 * <p>
 * 增强默认Ureport 行为
 */
@ConditionalOnClass(OssTemplate.class)
@Configuration(proxyBeanMethods = false)
public class UreportExtConfig {

	@Bean
	public ReportProvider dfsReportProvider(OssTemplate ossTemplate, OssProperties properties) {
		return new DfsReportProvider(ossTemplate, properties);
	}

	@Bean
	public UReportPropertyPlaceholderConfigurer uReportPropertyPlaceholderConfigurerPlus() {
		return new UReportPropertyPlaceholderConfigurerPlus();
	}

	@Bean
	@Primary
	public LocaleResolver localeResolver() {

		AcceptHeaderLocaleResolver lr = new AcceptHeaderLocaleResolver();

		// 设置默认区域,

		lr.setDefaultLocale(Locale.CHINA);

		return lr;

	}

}
