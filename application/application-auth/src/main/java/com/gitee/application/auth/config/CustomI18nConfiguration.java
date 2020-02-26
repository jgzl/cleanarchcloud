package com.gitee.application.auth.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Security message
 *
 * @author lihaifeng
 * @date 2018/7/30 17:32
 */
@Configuration
public class CustomI18nConfiguration {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:messages/messages_zh_CN");
        return messageSource;
    }
}
