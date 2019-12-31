/*
 * Copyright (c) 2019-2019
 */

package com.gitee.gateway.healthindicator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 */
@Slf4j
public class ReadinessHealthIndicator extends AbstractHealthIndicator {
  @Override
  protected void doHealthCheck(Builder builder) {
    builder.withDetail("timestamp", System.currentTimeMillis())
        .withDetail("IP", "127.0.0.1").up().build();
  }
}
