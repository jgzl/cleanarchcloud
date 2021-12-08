package com.pig4cloud.pigx.common.gateway.annotation;

import com.pig4cloud.pigx.common.gateway.configuration.DynamicRouteAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author lihaifeng
 * @date 2018/11/5
 * <p>
 * 开启pigx 动态路由
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DynamicRouteAutoConfiguration.class)
public @interface EnableDynamicRoute {

}
