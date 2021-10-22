package com.github.jgzl.common.core.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;

import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
public class SysOauth2Properties {

	/**
	 * accessTokenValiditySeconds, default: 1 day.
	 */
	private int accessTokenValiditySeconds = 60 * 60 * 24;

	/**
	 * refreshTokenValiditySeconds, default: 1 day.
	 */
	private int refreshTokenValiditySeconds = 60 * 60 * 24;

	/**
	 * the urls for permitAll.
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
