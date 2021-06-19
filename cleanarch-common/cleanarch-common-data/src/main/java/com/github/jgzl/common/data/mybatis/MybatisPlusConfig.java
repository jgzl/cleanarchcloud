package com.github.jgzl.common.data.mybatis;

import javax.sql.DataSource;

import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xuhang
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@MapperScan("com.github.jgzl.**.mapper")
public class MybatisPlusConfig {

	/**
	 * 分页插件
	 * @return
	 */
	@Bean
	public PaginationInnerInterceptor paginationInterceptor() {
		PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
		// paginationInterceptor.setOverflow(false);
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
		// paginationInterceptor.setLimit(500);
		return paginationInterceptor;
	}

	/**
	 * 乐观锁
	 * @return
	 */
	@Bean
	public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInnerInterceptor();
	}

	@Bean
	@ConditionalOnMissingBean
	public BaseDoMetaObjectHandler metaObjectHandler() {
		return new BaseDoMetaObjectHandler();
	}
}