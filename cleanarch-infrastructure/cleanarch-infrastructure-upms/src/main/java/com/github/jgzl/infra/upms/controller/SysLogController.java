package com.github.jgzl.infra.upms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.api.vo.PreLogVO;
import com.github.jgzl.common.api.vo.SysLogDTO;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.security.annotation.Inner;
import com.github.jgzl.infra.upms.service.SysLogService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author lengleng
 * @since 2017-11-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/log")
@Api(value = "log", tags = "日志管理模块")
public class SysLogController {

	private final SysLogService sysLogService;

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param sysLog 系统日志
	 * @return
	 */
	@GetMapping("/page")
	public Result getLogPage(Page page, SysLogDTO sysLog) {
		return Result.ok(sysLogService.getLogByPage(page, sysLog));
	}

	/**
	 * 删除日志
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_log_del')")
	public Result removeById(@PathVariable Long id) {
		return Result.ok(sysLogService.removeById(id));
	}

	/**
	 * 插入日志
	 * @param sysLog 日志实体
	 * @return success/false
	 */
	@Inner
	@PostMapping("/save")
	public Result save(@Valid @RequestBody SysLogDTO sysLog) {
		return Result.ok(sysLogService.saveLog(sysLog));
	}

	/**
	 * 批量插入前端异常日志
	 * @param preLogVoList 日志实体
	 * @return success/false
	 */
	@PostMapping("/logs")
	public Result saveBatchLogs(@RequestBody List<PreLogVO> preLogVoList) {
		return Result.ok(sysLogService.saveBatchLogs(preLogVoList));
	}

}
