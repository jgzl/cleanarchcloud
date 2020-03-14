package com.gitee.common.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sso")
public class SsoProperties {
	private String frontendUrl;

	private SsoOauth2Properties oauth2 = new SsoOauth2Properties();
}
