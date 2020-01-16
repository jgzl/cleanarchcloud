/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gitee.gateway.filter;

import java.time.Instant;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author lihaifeng
 */
@Slf4j
@Component
public class TimeStatsGlobalFilter implements GlobalFilter, Ordered {

  private static final String COUNT_START_TIME = "countStartTime";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    exchange.getAttributes().put(COUNT_START_TIME, Instant.now().toEpochMilli());
    return chain.filter(exchange).then(
        Mono.fromRunnable(() -> {
          long startTime = exchange.getAttribute(COUNT_START_TIME);
          long endTime = (Instant.now().toEpochMilli() - startTime);
          log.info("执行共消耗时间为：{}",exchange.getRequest().getURI().getRawPath() + ": " + endTime + "ms");
        })
    );
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
