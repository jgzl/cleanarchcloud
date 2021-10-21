package com.github.jgzl.common.core;

import com.github.jgzl.common.core.config.SysProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@EnableConfigurationProperties(SysProperties.class)
public class CoreAutoConfiguration {

}