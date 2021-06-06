package com.github.jgzl.swagger.annotation;

import java.lang.annotation.*;

import com.github.jgzl.swagger.config.GatewaySwaggerAutoConfiguration;
import com.github.jgzl.swagger.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

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
