package com.github.jgzl.infra.upms.controller.baseinfo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.annotation.log.SysLog;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.infra.upms.domain.dto.UserSaveDTO;
import com.github.jgzl.infra.upms.domain.dto.UserUpdateDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.User;
import com.github.jgzl.infra.upms.domain.enums.Sex;
import com.github.jgzl.infra.upms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.github.jgzl.infra.upms.domain.converts.UserConverts.USER_DTO_2_PO_CONVERTS;

/**
 * 用户管理
 *
 * @author lihaifeng
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

	/**
	 * 用户列表
	 * @param current
	 * @param size
	 * @param username 账号
	 * @param nickName 名称
	 * @param sex 性别
	 * @param email 邮箱
	 * @param orgId 组织
	 * @param mobile 手机号
	 * @return
	 */
    @GetMapping
    public Result<IPage<User>> query(@RequestParam(required = false, defaultValue = "1") Integer current,
									 @RequestParam(required = false, defaultValue = "20") Integer size,
									 String username, String nickName, Integer sex, String email, Long orgId, String mobile) {
        final IPage<User> page = this.userService.page(new Page<>(current, size),
                Wraps.<User>lbQ().eq(User::getSex, Sex.of(sex)).eq(User::getOrgId, orgId)
                        .like(User::getNickName, nickName).like(User::getMobile, mobile)
                        .like(User::getUsername, username).like(User::getEmail, email));
        return Result.success(page);
    }


    @PostMapping
    @SysLog(value = "添加用户")
    public Result<ResponseEntity<Void>> save(@Validated @RequestBody UserSaveDTO dto) {
        this.userService.addUser(dto);
        return Result.success();
    }


    @PutMapping("{id}")
    @SysLog(value = "编辑用户")
    public Result<ResponseEntity<Void>> edit(@PathVariable Long id, @Validated @RequestBody UserUpdateDTO dto) {
        this.userService.updateById(USER_DTO_2_PO_CONVERTS.convert(dto, id));
        return Result.success();
    }


    @DeleteMapping("{id}")
    @SysLog(value = "删除用户")
    public Result<ResponseEntity<Void>> del(@PathVariable Long id) {
        this.userService.deleteById(id);
        return Result.success();
    }
}
