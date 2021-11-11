package com.github.jgzl.infra.upms.controller.log;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.page.PageRequest;
import com.github.jgzl.infra.upms.domain.entity.log.OptLog;
import com.github.jgzl.infra.upms.service.OptLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 操作日志
 *
 * @author Levin
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/opt_logs")
@DynamicDS
@RequiredArgsConstructor
public class OptLogController {


    private final OptLogService optLogService;

    @GetMapping
    public Result<Page<OptLog>> query(PageRequest request, String location, String description) {
        final Page<OptLog> page = this.optLogService.page(request.buildPage(), Wraps.<OptLog>lbQ()
                .like(OptLog::getLocation, location)
                .like(OptLog::getDescription, description).orderByDesc(OptLog::getStartTime));
        return Result.success(page);
    }

    @DeleteMapping("/{day}")
    public Result<Void> batchDelete(@PathVariable Integer day) {
        this.optLogService.remove(Wraps.<OptLog>lbQ().le(OptLog::getStartTime, LocalDateTime.now().plusDays(-day)));
        return Result.success();
    }


}
