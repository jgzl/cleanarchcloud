package com.github.jgzl.common.gateway.annotation;

import com.github.jgzl.common.gateway.config.DynamicRouteConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author lihaifeng
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({DynamicRouteConfiguration.class})
public @interface EnableDynamicRoute {

}
