package com.github.jgzl.infra.codegen.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.api.vo.OptLogDTO;
import com.github.jgzl.common.core.util.RegionUtils;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.page.PageRequest;
import com.github.jgzl.infra.codegen.entity.OptLog;
import com.github.jgzl.infra.codegen.service.OptLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志
 * @author Levin
 */
@Slf4j
@RestController
@RequestMapping("/opt_logs")
@RequiredArgsConstructor
@Validated
public class OptLogController {

    private final OptLogService optLogService;

	/**
	 *
	 * @param request
	 * @param location 地区
	 * @param description 描述信息
	 * @return
	 */
    @GetMapping
    public Result<Page<OptLog>> query(PageRequest request, String location, String description) {
        final Page<OptLog> page = this.optLogService.page(request.buildPage(), Wraps.<OptLog>lbQ()
                .like(OptLog::getLocation, location)
                .like(OptLog::getDescription, description)
                .orderByDesc(OptLog::getStartTime));
        return Result.success(page);
    }

    @PostMapping
    public Result<ResponseEntity<Void>> save(@RequestBody OptLogDTO dto) {
        log.info("[日志信息] - {}", JSONUtil.toJsonStr(dto));
        final OptLog record = BeanUtil.toBean(dto, OptLog.class);
        record.setLocation(RegionUtils.getRegion(dto.getIp()));
        this.optLogService.save(record);
        return Result.success();
    }

}
