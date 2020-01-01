/*
 *    Copyright (c) 2018-2025, lihaifeng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the dlihaifeng.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lihaifeng (wangiegie@gmail.com)
 */

package com.gitee.common.core.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author lihaifeng
 * @date 2018/11/14
 * <p>
 * 国际化配置
 */
@Configuration
public class MessageSourceConfig {

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource
        = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:i18n/messages");
    return messageSource;
  }
}
