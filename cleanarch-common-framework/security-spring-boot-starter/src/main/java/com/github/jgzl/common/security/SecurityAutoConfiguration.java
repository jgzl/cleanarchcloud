package com.github.jgzl.common.security;
import com.github.jgzl.common.security.properties.FrameworkSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableConfigurationProperties(value = {
		FrameworkSecurityProperties.class
})
public class SecurityAutoConfiguration {

}