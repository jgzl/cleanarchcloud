package com.gitee.common.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import com.gitee.common.core.common.PropertyCache;
import com.gitee.common.core.http.DefaultHttpClient;
import com.gitee.common.core.http.HttpClientProperties;
import com.gitee.common.core.initializer.CoreApplicationRunner;
import com.gitee.common.core.thread.ThreadMonitor;
import com.gitee.common.core.thread.ThreadPool;
import com.gitee.common.core.thread.ThreadPoolProperties;

import lombok.Getter;

/**
 * @author: lihaifeng
 * @version: 2019-05-27 11:19
 **/
@Configuration
@Import(WebConfiguration.class)
@EnableConfigurationProperties({CoreProperties.class, HttpClientProperties.class, ThreadPoolProperties.class})
@Getter
public class BsfConfiguration {

    @Bean(initMethod = "close")
    @Lazy
    public DefaultHttpClient getDefaultHttpClient() {
        if (DefaultHttpClient.Default == null || DefaultHttpClient.Default.isClose()) {
            DefaultHttpClient.initDefault();
        }
        return DefaultHttpClient.Default;
    }

    @Bean(initMethod = "shutdown")
    @Lazy
    public ThreadPool getSystemThreadPool() {
        if (ThreadPool.System == null || ThreadPool.System.isShutdown()) {
            ThreadPool.initSystem();
        }
        return ThreadPool.System;
    }

    @Bean
    @ConditionalOnBean(ThreadPool.class)
    @Lazy
    public ThreadMonitor getSystemThreadPoolMonitor() {
        return ThreadPool.System.getThreadMonitor();
    }

    @Bean
    @Lazy
    public PropertyCache getPropertyCache() {
        PropertyCache.Default.clear();
        return PropertyCache.Default;
    }

    @Bean
    public CoreApplicationRunner coreApplicationRunner() {
        return new CoreApplicationRunner();
    }
}
