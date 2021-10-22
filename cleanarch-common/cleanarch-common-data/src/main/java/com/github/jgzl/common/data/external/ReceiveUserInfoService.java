package com.github.jgzl.common.data.external;

import com.github.jgzl.common.api.vo.SysUserVo;

/**
 * 依赖反转，获取当前用户账户名接口
 */
public interface ReceiveUserInfoService {
	SysUserVo getCurrentUserAccount();
}
