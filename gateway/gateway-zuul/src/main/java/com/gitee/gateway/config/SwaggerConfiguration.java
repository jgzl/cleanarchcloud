package com.gitee.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lihaifeng
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  @Order(value = 1)
  public Docket groupRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(groupApiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.dlihaifeng.conversion.platform.gateway.controller"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo groupApiInfo() {
    return new ApiInfoBuilder()
        .title("转换平台Zuul网关")
        .description("<div style='font-size:14px;color:red;'>转换平台Zuul网关,用于调用转换平台所有微服务实例</div>")
        .termsOfServiceUrl("http://www.dlihaifeng.com/")
        .contact(new Contact("李海峰", "http://gateway-zuul", "li7hai26@gmail.com"))
        .version("2.0")
        .build();
  }
}
