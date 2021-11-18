package com.github.jgzl.infra.upms.controller.baseinfo;

import cn.hutool.core.collection.CollectionUtil;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.mybatis.auth.DataScope;
import com.github.jgzl.common.data.mybatis.auth.DataScopeService;
import com.github.jgzl.common.security.annotation.Inner;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Role;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.User;
import com.github.jgzl.infra.upms.domain.vo.DictResp;
import com.github.jgzl.infra.upms.service.RoleService;
import com.github.jgzl.infra.upms.service.UserService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 用户管理
 *
 * @author lihaifeng
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthorityController {

    private final UserService userService;
    private final RoleService roleService;

    private static final int RECEIVER_TYPE_1 = 1;
    private static final int RECEIVER_TYPE_2 = 2;


    @GetMapping("/list/{receiver_type}/users_or_roles")
    public Result<List<DictResp>> list(@PathVariable("receiver_type") Integer receiverType) {
        List<DictResp> result = Lists.newArrayList();
        // 查询角色
        if (receiverType == RECEIVER_TYPE_1) {
            final List<User> users = this.userService.list(new DataScope());
            if (CollectionUtil.isNotEmpty(users)) {
                result = users.stream().map(user -> DictResp.builder()
                        .label(user.getNickName()).value(user.getId()).build()).collect(toList());
            }
        } else if (receiverType == RECEIVER_TYPE_2) {
            final List<Role> roles = this.roleService.list(new DataScope());
            if (CollectionUtil.isNotEmpty(roles)) {
                result = roles.stream().map(role -> DictResp.builder()
                        .label(role.getName()).value(role.getId()).build()).collect(toList());
            }
        }
        return Result.success(result);
    }

    private final DataScopeService dataScopeService;

    @Inner
    @GetMapping("/list")
    public Result<DataScope> list() {
        return Result.success(dataScopeService.getDataScopeById(7L));
    }


}
