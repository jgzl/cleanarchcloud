package com.github.jgzl.infra.upms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.annotation.log.SysLog;
import com.github.jgzl.common.core.exception.CheckedException;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.OAuthClientDetails;
import com.github.jgzl.infra.upms.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 应用管理
 *
 * @author Levin
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {


    private final ApplicationService applicationService;

    @GetMapping
    public Result<IPage<OAuthClientDetails>> query(@RequestParam(required = false, defaultValue = "1") Integer current,
												   @RequestParam(required = false, defaultValue = "20") Integer size,
												   String clientId, String clientName) {
        final IPage<OAuthClientDetails> page = this.applicationService.page(new Page<>(current, size),
                Wraps.<OAuthClientDetails>lbQ().like(OAuthClientDetails::getClientId, clientId)
                        .like(OAuthClientDetails::getClientName, clientName));
        return Result.success(page);
    }

    @PostMapping
    @SysLog(value = "添加应用")
    public Result<ResponseEntity<Void>> save(@Validated @RequestBody OAuthClientDetails dto) {
        final long count = this.applicationService.count(Wraps.<OAuthClientDetails>lbQ().eq(OAuthClientDetails::getClientId, dto.getClientId()));
        if (count > 0) {
            throw CheckedException.badRequest("客户ID已存在");
        }
        this.applicationService.save(dto);
        return Result.success();
    }

    @PutMapping("/{id}")
    @SysLog(value = "修改应用")
    public Result<ResponseEntity<Void>> edit(@PathVariable String id, @Validated @RequestBody OAuthClientDetails dto) {
        this.applicationService.updateById(dto);
        return Result.success();
    }

    @PutMapping("/{id}/{status}")
    @SysLog(value = "修改应用")
    public Result<ResponseEntity<Void>> status(@PathVariable String id, @PathVariable Boolean status) {
        this.applicationService.updateById(OAuthClientDetails.builder().clientId(id).status(status).build());
        return Result.success();
    }

    @DeleteMapping("{id}")
    @SysLog(value = "删除应用")
    public Result<ResponseEntity<Void>> del(@PathVariable String id) {
        this.applicationService.removeById(id);
        return Result.success();
    }


}
