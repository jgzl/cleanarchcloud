package com.gitee.swagger.annotation;

import java.lang.annotation.*;

import com.gitee.swagger.config.GatewaySwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import com.gitee.swagger.config.SwaggerAutoConfiguration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lihaifeng
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableSwagger2
@Import({ SwaggerAutoConfiguration.class, GatewaySwaggerAutoConfiguration.class })
public @interface EnableSwaggerPlus {
}
