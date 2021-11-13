package com.github.jgzl.common.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihaifeng
 */
@Data
@ConfigurationProperties(prefix = FrameworkSecurityProperties.PREFIX)
public class FrameworkSecurityProperties {

	public static final String PREFIX = "framework.security";
	
	private String upmsUrl;
	private String frontendUrl;
	private String redirectUrl;
	private String systemCode;
	private String integrationCode;

	/**
	 * accessTokenValiditySeconds, default: 1 day.
	 */
	private int accessTokenValiditySeconds = 60 * 60 * 24;

	/**
	 * refreshTokenValiditySeconds, default: 1 day.
	 */
	private int refreshTokenValiditySeconds = 60 * 60 * 24;

	/**
	 * 全部微服务公共暴露URL(统一使用配置中心进行配置)
	 */
	private List<String> urlPermitAll = new ArrayList<>();

	/**
	 * The key store properties for locating a key in a Java Key Store (a file in a format
	 * defined and understood by the JVM).
	 */
	private KeyStore keyStore = new KeyStore();

	@Data
	public static class KeyStore {

		/**
		 * Location of the key store file, e.g. classpath:/keystore.jks.
		 */
		private Resource location;

		/**
		 * Password that locks the keystore.
		 */
		private String password;

		/**
		 * Alias for a key in the store.
		 */
		private String alias;

		/**
		 * Secret protecting the key (defaults to the same as the password).
		 */
		private String secret;

	}
}
