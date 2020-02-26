package com.gitee.application.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.common.security.dao.SsoUserDAO;
import com.gitee.common.security.vo.SsoUserVO;

/**
 * @author Administrator
 */
public interface SsoUserService extends IService<SsoUserDAO> {
	public SsoUserVO findUserByUsername(String username);
	public SsoUserVO findUserByMobile(String mobile);
}
