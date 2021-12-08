package com.github.jgzl.common.gateway.configuration;

import com.github.jgzl.common.gateway.filter.GrayReactiveLoadBalancerClientFilter;
import com.github.jgzl.common.gateway.rule.GrayLoadBalancer;
import com.github.jgzl.common.gateway.rule.VersionGrayLoadBalancer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClientsProperties;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mica ribbon rule auto configuration.
 *
 * @author L.cm
 * @link https://github.com/lets-mica/mica
 */
@Configuration
@EnableConfigurationProperties(LoadBalancerClientsProperties.class)
@ConditionalOnProperty(value = "gray.rule.enabled", havingValue = "true")
@AutoConfigureAfter(LoadBalancerAutoConfiguration.class)
@AutoConfigureBefore(GatewayReactiveLoadBalancerClientAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class GrayLoadBalancerClientConfiguration {

	@Bean
	public ReactiveLoadBalancerClientFilter gatewayLoadBalancerClientFilter(GrayLoadBalancer grayLoadBalancer,
																			LoadBalancerClientsProperties properties, GatewayLoadBalancerProperties loadBalancerProperties) {
		return new GrayReactiveLoadBalancerClientFilter(loadBalancerProperties, properties, grayLoadBalancer);
	}

	@Bean
	public GrayLoadBalancer grayLoadBalancer(DiscoveryClient discoveryClient) {
		return new VersionGrayLoadBalancer(discoveryClient);
	}

}
