/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
@Configuration
public class CustomNacosConfig {

	@Autowired
	private CustomNacosProperties nacosProperties;

	/**
	 * 流控实体转换为json
	 * @return
	 */
	@Bean
	public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	/**
	 * json转换为流控实体
	 * @return
	 */
	@Bean
	public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
		return s -> JSON.parseArray(s, FlowRuleEntity.class);
	}

	/**
	 * 授权实体转换为json
	 * @return
	 */
	@Bean
	public Converter<List<AuthorityRuleEntity>, String> authorityRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	/**
	 * json转换为授权实体
	 * @return
	 */
	@Bean
	public Converter<String, List<AuthorityRuleEntity>> authorityRuleEntityDecoder() {
		return s -> JSON.parseArray(s, AuthorityRuleEntity.class);
	}

	/**
	 * 降级实体转换为json
	 * @return
	 */
	@Bean
	public Converter<List<DegradeRuleEntity>, String> degradeRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	/**
	 * json转换为授权实体
	 * @return
	 */
	@Bean
	public Converter<String, List<DegradeRuleEntity>> degradeRuleEntityDecoder() {
		return s -> JSON.parseArray(s, DegradeRuleEntity.class);
	}

	/**
	 * 热点参数实体转换为json
	 * @return
	 */
	@Bean
	public Converter<List<ParamFlowRuleEntity>, String> paramFlowRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	/**
	 * json转换为热点参数实体
	 * @return
	 */
	@Bean
	public Converter<String, List<ParamFlowRuleEntity>> paramFlowRuleEntityDecoder() {
		return s -> JSON.parseArray(s, ParamFlowRuleEntity.class);
	}

	/**
	 * 系统参数实体转换为json
	 * @return
	 */
	@Bean
	public Converter<List<SystemRuleEntity>, String> systemRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	/**
	 * json转换为系统参数实体
	 * @return
	 */
	@Bean
	public Converter<String, List<SystemRuleEntity>> systemRuleEntityDecoder() {
		return s -> JSON.parseArray(s, SystemRuleEntity.class);
	}

	/**
	 * 网关流控参数实体转换为json
	 * @return
	 */
	@Bean
	public Converter<List<ApiDefinitionEntity>, String> apiDefinitionEntityEncoder() {
		return JSON::toJSONString;
	}

	/**
	 * json转换为网关流控参数实体
	 * @return
	 */
	@Bean
	public Converter<String, List<ApiDefinitionEntity>> apiDefinitionEntityDecoder() {
		return s -> JSON.parseArray(s, ApiDefinitionEntity.class);
	}

	/**
	 * 网关Api分组定义实体转换为json
	 * @return
	 */
	@Bean
	public Converter<List<GatewayFlowRuleEntity>, String> gatewayFlowRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	/**
	 * json转换为网关Api分组定义实体
	 * @return
	 */
	@Bean
	public Converter<String, List<GatewayFlowRuleEntity>> gatewayFlowRuleEntityDecoder() {
		return s -> JSON.parseArray(s, GatewayFlowRuleEntity.class);
	}

	@Bean
	public ConfigService nacosConfigService() throws Exception {
		Properties properties = new Properties();
		properties.setProperty(PropertyKeyConst.SERVER_ADDR, nacosProperties.getServerAddr());
		properties.setProperty(PropertyKeyConst.NAMESPACE, nacosProperties.getNamespace());
		return ConfigFactory.createConfigService(properties);
	}
}
