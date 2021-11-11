package com.github.jgzl.infra.upms.controller.log;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.page.PageRequest;
import com.github.jgzl.infra.upms.domain.entity.log.LoginLog;
import com.github.jgzl.infra.upms.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录日志
 *
 * @author Levin
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/login_logs")
@RequiredArgsConstructor
public class LoginLogController {

    private final LoginLogService loginLogService;

	/**
	 * 查询日志
	 * @param request
	 * @param name 名称
	 * @param principal 账号
	 * @return
	 */
    @GetMapping
    public Result<Page<LoginLog>> query(PageRequest request, String name, String principal) {
        final Page<LoginLog> page = this.loginLogService.page(request.buildPage(), Wraps.<LoginLog>lbQ()
                .like(LoginLog::getName, name)
                .like(LoginLog::getPrincipal, principal).orderByDesc(LoginLog::getCreatedTime));
        return Result.success(page);
    }


}
