package com.dlihaifeng.conversion.platform.gateway.healthindicator;

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
    builder.withDetail("usercount", 10)
        .withDetail("userstatus", "up").up().build();
  }
}
