package com.github.jgzl.common.core.config;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jgzl.common.core.jackson.CustomJavaTimeModule;

import cn.hutool.core.date.DatePattern;

/**
 * JacksonConfig
 * 必须是json传入才会走这个自动序列化反序列化LocalDateTime,LocalDate,LocalTime
 *
 * @author: lihaifeng
 * @author L.cm
 * @author: lishangbu
 * @date: 2018/10/22
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfig {
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer customizer() {
		return builder -> {
			builder.locale(Locale.CHINA);
			builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
			builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
			builder.modules(new CustomJavaTimeModule());
		};
	}
}
