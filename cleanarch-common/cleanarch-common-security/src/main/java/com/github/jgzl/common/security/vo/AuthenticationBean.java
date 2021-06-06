package com.github.jgzl.common.security.vo;

import lombok.Data;

/**
 * 用户认证的Bean
 *
 * @author lihaifeng
 * 2019/7/3 14:11
 */
@Data
public class AuthenticationBean {

    private String username;

    private String password;
}
