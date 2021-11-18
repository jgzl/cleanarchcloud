package com.github.jgzl.infra.upms.service;

import com.github.jgzl.common.core.util.Result;
import com.wf.captcha.base.Captcha;

/**
 * @author lihaifeng
 */
public interface VerificationService {

    /**
     * 创建验证码
     *
     * @param key key
     * @return 验证码结果
     */
    Captcha create(String key);

    /**
     * 验证图形验证码
     *
     * @param key   key
     * @param value val
     * @return 验证结果
     */
    Result<Boolean> valid(String key, String value);

}
