package com.github.jgzl.common.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Mybatis 配置
 *
 * @author lihaifeng
 * @date 2021/6/3
 */
@Data
@RefreshScope
@ConfigurationProperties("extend.mybatis")
public class MybatisProperties {

	/**
	 * 是否打印可执行 sql
	 */
	private boolean showSql = true;

}
