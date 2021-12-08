package com.github.jgzl.infra.upms.service;

import com.github.jgzl.common.core.util.R;

/**
 * @author lihaifeng
 * @date 2018/11/14
 */
public interface MobileService {

	/**
	 * 发送手机验证码
	 * @param mobile mobile
	 * @return code
	 */
	R<Boolean> sendSmsCode(String mobile);

}
