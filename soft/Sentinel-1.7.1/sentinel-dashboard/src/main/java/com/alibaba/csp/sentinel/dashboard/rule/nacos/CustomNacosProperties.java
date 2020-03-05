package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Objects;

/**
 * @author lihaifeng
 */
@Configuration
@ConfigurationProperties(prefix = "nacos")
public class CustomNacosProperties {
	private String serverAddr;

	private String namespace;

	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Override
	public String toString() {
		return "CustomNacosProperties{" +
				"serverAddr='" + serverAddr + '\'' +
				", namespace='" + namespace + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CustomNacosProperties that = (CustomNacosProperties) o;
		return Objects.equal(serverAddr, that.serverAddr) &&
				Objects.equal(namespace, that.namespace);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(serverAddr, namespace);
	}
}
