package com.github.jgzl.infra.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.SocialUser;

import java.util.List;

/**
 * 社会登录用户管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
public interface SysSocialUserService extends IService<SocialUser> {

	/**
	 * 根据用户ID和平台来源查找所有的平台用户信息
	 * @param userId
	 * @param source
	 * @return
	 */
	List<SocialUser> findSocialUserByUserIdAndSource(String userId, String source);
}
