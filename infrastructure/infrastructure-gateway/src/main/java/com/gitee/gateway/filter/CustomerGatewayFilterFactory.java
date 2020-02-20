/*
 * Copyright (c) 2019-2019
 *//*


package com.gitee.conversion.platform.gateway.filter;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.gitee.conversion.platform.gateway.filter.CustomerGatewayFilterFactory.Config;

import reactor.core.publisher.Mono;

@Component
public class CustomerGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

  private static final Logger log = LoggerFactory.getLogger(CustomerGatewayFilterFactory.class);

  private static final String COUNT_START_TIME = "countStartTime";

  @Override
  public List<String> shortcutFieldOrder() {
    return Arrays.asList("enabled");
  }

  public CustomerGatewayFilterFactory() {
    super(Config.class);
    log.info("Loaded GatewayFilterFactory [CustomerGatewayFilterFactory]");
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      if (!config.isEnabled()) {
        return chain.filter(exchange);
      }
      exchange.getAttributes().put(COUNT_START_TIME, System.currentTimeMillis());
      return chain.filter(exchange).then(
          Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(COUNT_START_TIME);
            if (startTime != null) {
              StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath())
                  .append(": ")
                  .append(System.currentTimeMillis() - startTime)
                  .append("ms");
              sb.append(" params:").append(exchange.getRequest().getQueryParams());
              log.info(sb.toString());
            }
          })
      );
    };
  }

  public static class Config {
    */
/**
 * 控制是否开启统计
 *//*

    private boolean enabled;

    public Config() {
    }

    public boolean isEnabled() {
      return enabled;
    }

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }
  }
}
*/
