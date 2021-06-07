package com.github.jgzl.gateway.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 过滤器定义模型
 * @author lihaifeng
 */
@Data
public class GatewayFilterDefinition {

	/**
	 * Filter Name
	 */
	private String name;

	/**
	 * 对应的路由规则
	 */
	private Map<String, String> args = new LinkedHashMap<>();

}
