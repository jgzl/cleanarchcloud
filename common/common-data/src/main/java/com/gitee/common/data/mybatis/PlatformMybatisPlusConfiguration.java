package com.gitee.common.data.mybatis;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

/**
 * @author lihaifeng
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@MapperScan("com.gitee.application.**.**.mapper")
public class PlatformMybatisPlusConfiguration {
  @Bean
  @ConditionalOnMissingBean
  public PaginationInterceptor paginationInterceptor(){
    // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
    // paginationInterceptor.setOverflow(false);
    // 设置最大单页限制数量，默认 500 条，-1 不受限制
    // paginationInterceptor.setLimit(500);
    return new PaginationInterceptor();
  }
  /**
   * SQL执行效率插件
   */
  @Bean
  @Profile({"dev","test"})// 设置 dev test 环境开启
  @ConditionalOnMissingBean
  public PerformanceInterceptor performanceInterceptor() {
    return new PerformanceInterceptor();
  }
  @Bean
  @ConditionalOnMissingBean
  public PlatformMetaObjectHandler metaObjectHandler(){
    return new PlatformMetaObjectHandler();
  }
}
