package com.github.jgzl.common.data;
import com.github.jgzl.common.data.properties.FrameworkMpProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableConfigurationProperties(value = {
		FrameworkMpProperties.class
})
public class DataAutoConfiguration {

}
