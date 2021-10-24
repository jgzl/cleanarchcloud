package com.github.jgzl.common.core;

import com.github.jgzl.common.core.properties.SecurityConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableConfigurationProperties(SecurityConfigProperties.class)
public class CoreAutoConfiguration {

}