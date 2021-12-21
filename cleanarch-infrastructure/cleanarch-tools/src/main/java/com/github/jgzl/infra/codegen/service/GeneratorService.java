package com.github.jgzl.infra.codegen.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.infra.codegen.entity.GenConfig;

import java.util.Map;

/**
 * @author lihaifeng
 * @date 2018/7/29
 */
public interface GeneratorService {

	/**
	 * 生成代码
	 *
	 * @param genConfig 生成信息
	 * @return
	 */
	byte[] generatorCode(GenConfig genConfig);

	/**
	 * 分页查询表
	 *
	 * @param page      分页信息
	 * @param tableName 表名
	 * @param name      数据源ID
	 * @return
	 */
	IPage<Map<String, Object>> getPage(Page page, String tableName, String name);

	/**
	 * 预览代码
	 *
	 * @param genConfig 生成信息
	 * @return
	 */
	Map<String, String> previewCode(GenConfig genConfig);

}
