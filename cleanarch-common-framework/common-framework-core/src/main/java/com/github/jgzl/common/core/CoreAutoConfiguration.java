package com.github.jgzl.common.core;
import com.github.jgzl.common.core.properties.FrameworkSecurityConfigProperties;
import com.github.jgzl.common.core.properties.TenantConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableConfigurationProperties(value = {
		FrameworkSecurityConfigProperties.class,
		TenantConfigProperties.class,
})
public class CoreAutoConfiguration {

}