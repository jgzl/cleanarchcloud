package com.github.jgzl.common.datasource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lihaifeng
 * @date 2019-05-14
 * <p>
 * 参考DruidDataSourceWrapper
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class ExtendDataSourceProperties {

	/**
	 * 数据源用户名
	 */
	private String username;

	/**
	 * 数据源密码
	 */
	private String password;

	/**
	 * jdbcurl
	 */
	private String url;

	/**
	 * 数据源驱动
	 */
	private String driverClassName;

	/**
	 * 查询数据源的SQL
	 */
	private String queryDsSql = "select * from gen_datasource_conf where del_flag = 0";

}
