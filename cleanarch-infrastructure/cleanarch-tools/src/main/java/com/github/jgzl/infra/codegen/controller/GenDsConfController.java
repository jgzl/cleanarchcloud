package com.github.jgzl.infra.codegen.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.util.R;
import com.github.jgzl.common.log.annotation.SysLog;
import com.github.jgzl.infra.codegen.entity.GenDatasourceConf;
import com.github.jgzl.infra.codegen.service.GenDatasourceConfService;
import com.pig4cloud.pigx.common.xss.core.XssCleanIgnore;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 数据源管理
 *
 * @author lihaifeng
 * @date 2019-03-31 16:00:20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dsconf")
public class GenDsConfController {

	private final GenDatasourceConfService datasourceConfService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param datasourceConf 数据源表
	 * @return
	 */
	@GetMapping("/page")
	public R getSysDatasourceConfPage(Page page, GenDatasourceConf datasourceConf) {
		return R.ok(datasourceConfService.page(page, Wrappers.query(datasourceConf)));
	}

	/**
	 * 查询全部数据源
	 * @return
	 */
	@GetMapping("/list")
	public R list() {
		return R.ok(datasourceConfService.list());
	}

	/**
	 * 通过id查询数据源表
	 * @param id id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(datasourceConfService.getById(id));
	}

	/**
	 * 新增数据源表
	 * @param datasourceConf 数据源表
	 * @return R
	 */
	@SysLog("新增数据源表")
	@PostMapping
	@XssCleanIgnore
	public R save(@RequestBody GenDatasourceConf datasourceConf) {
		return R.ok(datasourceConfService.saveDsByEnc(datasourceConf));
	}

	/**
	 * 修改数据源表
	 * @param conf 数据源表
	 * @return R
	 */
	@SysLog("修改数据源表")
	@PutMapping
	@XssCleanIgnore
	public R updateById(@RequestBody GenDatasourceConf conf) {
		return R.ok(datasourceConfService.updateDsByEnc(conf));
	}

	/**
	 * 通过id删除数据源表
	 * @param id id
	 * @return R
	 */
	@SysLog("删除数据源表")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {
		return R.ok(datasourceConfService.removeByDsId(id));
	}

}
