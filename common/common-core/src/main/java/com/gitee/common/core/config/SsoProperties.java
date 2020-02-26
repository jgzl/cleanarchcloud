package com.gitee.common.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Configuration
@EnableConfigurationProperties(SsoOauth2Properties.class)
public class SsoProperties {
}
