package com.github.jgzl.common.gateway.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.jgzl.common.gateway.config.DynamicRouteConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author lihaifeng
 * ComponentScan一定要加，否则无法扫描到相关的common网关配置
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan(basePackages = {"com.github.jgzl.common.gateway"})
@Import({DynamicRouteConfiguration.class})
public @interface EnableDynamicSpringCloudGateway {
}
