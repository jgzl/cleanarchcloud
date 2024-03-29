package com.github.jgzl.infra.codegen.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 数据源表
 *
 * @author lihaifeng
 * @date 2019-03-31 16:00:20
 */
@Data
@TableName("gen_datasource_conf")
@EqualsAndHashCode(callSuper = true)
public class GenDatasourceConf extends Model<GenDatasourceConf> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 数据库类型
	 */
	private String dsType;

	/**
	 * 配置类型 （0 主机形式 | 1 url形式）
	 */
	private Integer confType;

	/**
	 * 主机地址
	 */
	private String host;

	/**
	 * 端口
	 */
	private Integer port;

	/**
	 * jdbc-url
	 */
	private String url;

	/**
	 * 实例
	 */
	private String instance;

	/**
	 * 数据库名称
	 */
	private String dsName;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 创建时间
	 */
	private LocalDateTime createDate;

	/**
	 * 更新
	 */
	private LocalDateTime updateDate;

	/**
	 * 删除标记
	 */
	@TableLogic
	private String delFlag;

}
