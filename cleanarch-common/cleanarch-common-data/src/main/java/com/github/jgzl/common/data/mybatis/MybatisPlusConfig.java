package com.github.jgzl.common.data.mybatis;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.github.jgzl.common.data.external.ReceiveUserInfoService;
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
	 * 新版
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		// 租户
//		mybatisPlusInterceptor.addInnerInterceptor(new TenantLineInnerInterceptor());
		// 乐观锁
		mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		// 分页
		mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		return mybatisPlusInterceptor;
	}

	@Bean
	@ConditionalOnMissingBean
	public BaseDoMetaObjectHandler metaObjectHandler(@NotNull ReceiveUserInfoService receiveUserInfoService) {
		return new BaseDoMetaObjectHandler(receiveUserInfoService);
	}
}