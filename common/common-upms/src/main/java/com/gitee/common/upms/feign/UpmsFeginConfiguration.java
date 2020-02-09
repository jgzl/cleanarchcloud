package com.gitee.common.upms.feign;

import org.springframework.context.annotation.Bean;

import feign.Logger;
import feign.Logger.Level;

public class UpmsFeginConfiguration {
  @Bean
  public Logger.Level level(){
    return Level.BASIC;
//    return Level.HEADERS;
//  return Level.FULL;
  }
}
