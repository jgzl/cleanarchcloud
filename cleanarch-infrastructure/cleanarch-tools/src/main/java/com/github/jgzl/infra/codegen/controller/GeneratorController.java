package com.github.jgzl.infra.codegen.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.util.R;
import com.github.jgzl.infra.codegen.entity.GenConfig;
import com.github.jgzl.infra.codegen.service.GenTableColumnService;
import com.github.jgzl.infra.codegen.service.GeneratorService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成器
 *
 * @author lihaifeng
 * @date 2018-07-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/generator")
public class GeneratorController {

	private final GeneratorService generatorService;

	private final GenTableColumnService columnService;

	/**
	 * 列表
	 * @param tableName 参数集
	 * @param dsName 数据源编号
	 * @return 数据库表
	 */
	@GetMapping("/page")
	public R getPage(Page page, String tableName, String dsName) {
		return R.ok(generatorService.getPage(page, tableName, dsName));
	}

	/**
	 * 预览代码
	 * @param genConfig 数据表配置
	 * @return
	 */
	@GetMapping("/preview")
	public R previewCode(GenConfig genConfig) {
		return R.ok(generatorService.previewCode(genConfig));
	}

	/**
	 * 查询表的列信息
	 * @param page 分页
	 * @param genConfig 数据表配置
	 * @return
	 */
	@GetMapping("/table")
	public R getTable(Page page, GenConfig genConfig) {
		return R.ok(columnService.listTable(page, genConfig));
	}

	/**
	 * 生成代码
	 * @param genConfig 数据表配置
	 */
	@SneakyThrows
	@PostMapping("/code")
	public void generatorCode(@RequestBody GenConfig genConfig, HttpServletResponse response) {
		byte[] data = generatorService.generatorCode(genConfig);
		response.reset();
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				String.format("attachment; filename=%s.zip", genConfig.getTableName()));
		response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
		response.setContentType("application/octet-stream; charset=UTF-8");

		IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
	}

}
