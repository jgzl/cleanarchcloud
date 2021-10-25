package com.github.jgzl.infra.log.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.api.vo.PreLogVo;
import com.github.jgzl.common.api.vo.SysLogDTO;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.security.annotation.Inner;
import com.github.jgzl.infra.log.service.SysLogService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * 日志表 前端控制器
 *
 * @author lihaifeng
 * @since 2017-11-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/log")
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
	public Result<Boolean> save(@Valid @RequestBody SysLogDTO sysLog) {
		return Result.ok(sysLogService.saveLog(sysLog));
	}

	/**
	 * 批量插入前端异常日志
	 * @param preLogVoList 日志实体
	 * @return success/false
	 */
	@PostMapping("/logs")
	public Result saveBatchLogs(@RequestBody List<PreLogVo> preLogVoList) {
		return Result.ok(sysLogService.saveBatchLogs(preLogVoList));
	}

}
