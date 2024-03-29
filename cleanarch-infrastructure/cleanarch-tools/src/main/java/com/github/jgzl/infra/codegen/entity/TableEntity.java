package com.github.jgzl.infra.codegen.entity;

import lombok.Data;

import java.util.List;

/**
 * @author lihaifeng
 * @date 2018/07/29 表属性： https://blog.csdn.net/lkforce/article/details/79557482
 */
@Data
public class TableEntity {

	/**
	 * 名称
	 */
	private String tableName;

	/**
	 * 备注
	 */
	private String comments;

	/**
	 * 主键
	 */
	private ColumnEntity pk;

	/**
	 * 列名
	 */
	private List<ColumnEntity> columns;

	/**
	 * 驼峰类型
	 */
	private String caseClassName;

	/**
	 * 普通类型
	 */
	private String lowerClassName;

	/**
	 * 数据库类型 （用于根据数据库个性化）
	 */
	private String dbType;

}
