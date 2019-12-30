/*
 * Copyright (c) 2019-2019
 */

package com.dlihaifeng.conversion.platform.gateway.config;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthIndicatorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dlihaifeng.conversion.platform.gateway.healthindicator.ReadinessHealthIndicator;

/**
 * @author lihaifeng
 */
@Configuration
@ConditionalOnEnabledHealthIndicator("readiness")
@AutoConfigureAfter(value = HealthEndpointAutoConfiguration.class)
public class ReadinessHealthIndicatorAutoConfiguration extends
    CompositeHealthIndicatorConfiguration<ReadinessHealthIndicator, String> {
  @Bean
  @ConditionalOnMissingBean(name = "readinessHealthIndicator")
  public ReadinessHealthIndicator readinessHealthIndicator() {
    return new ReadinessHealthIndicator();
  }
}
