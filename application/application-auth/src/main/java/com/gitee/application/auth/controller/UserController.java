package com.gitee.application.auth.controller;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gitee.application.auth.service.MockUserService;
import com.gitee.common.core.constant.CacheConstants;
import com.gitee.common.core.util.Response;
import com.gitee.common.data.redis.CustomRedisRepository;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户Controller
 *
 * @author lihaifeng
 * 2019/7/4 15:44
 */
@Slf4j
@Controller
@Validated
public class UserController {

    private static final String MOBILE_REG = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$";

    @Autowired
    private CustomRedisRepository redisRepository;

    @Autowired
    private MockUserService userService;

    /**
     * 查询登录用户
     *
     * @param authentication 信息
     * @return 用户信息
     */
    @RequestMapping("/user")
    @ResponseBody
    public ResponseEntity<Response> user(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return ResponseEntity.ok(Response.success(authentication.getPrincipal()));
    }

    /**
     * 发送手机验证码
     *
     * @param mobile
     * @return
     */
    @RequestMapping("/smsCode/{mobile}")
    @ResponseBody
    public ResponseEntity<Response> smsCode(@Pattern(regexp = MOBILE_REG, message = "请输入正确的手机号")
                                            @PathVariable String mobile) {
        Object tempCode = redisRepository.get(CacheConstants.DEFAULT_CODE_KEY + mobile);
        if (tempCode != null) {
            log.error("用户:{}验证码未失效{}", mobile, tempCode);
            return ResponseEntity.ok(Response.failure("验证码: " + tempCode + " 未失效，请失效后再次申请"));
        }
        if (userService.findUserByMobile(mobile) == null) {
            log.error("根据用户手机号:{}, 查询用户为空", mobile);
            return ResponseEntity.ok(Response.failure("手机号不存在"));
        }
        String code = RandomUtil.randomNumbers(6);
        log.info("短信发送请求消息中心 -> 手机号:{} -> 验证码：{}", mobile, code);
        redisRepository.setExpire(CacheConstants.DEFAULT_CODE_KEY + mobile, code, CacheConstants.DEFAULT_EXPIRE_SECONDS);
        return ResponseEntity.ok(Response.success(code));
    }
}
