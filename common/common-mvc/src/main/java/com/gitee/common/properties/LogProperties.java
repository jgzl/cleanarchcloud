package com.gitee.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class LogProperties {

	private static String serviceName;

	public static String getServiceName() {
		return serviceName;
	}

	@Value("${spring.application.name}")
	public void setServiceName(String serviceName) {
		LogProperties.serviceName = serviceName;
	}
}
