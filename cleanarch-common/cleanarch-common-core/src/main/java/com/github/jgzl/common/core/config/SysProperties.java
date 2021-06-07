package com.github.jgzl.common.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sso")
public class SysProperties {
	
	private String frontendUrl;

	private SysOauth2Properties oauth2 = new SysOauth2Properties();
}
