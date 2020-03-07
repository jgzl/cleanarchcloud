package com.gitee.application.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.common.security.dao.SsoUserDAO;
import com.gitee.common.security.vo.SsoUserVO;
import com.gitee.common.security.vo.UserVO;

/**
 * @author Administrator
 */
public interface SsoUserService extends IService<SsoUserDAO> {
	UserVO findUserByUsername(String username);

	UserVO findUserByMobile(String mobile);

	/**
	 * 查询用户VO
	 *
	 * @param id
	 * @return
	 */
	SsoUserVO getVo(String id);

	/**
	 * 更新用户
	 *
	 * @param vo 用户
	 * @return
	 */
	Boolean update(SsoUserVO vo);

	/**
	 * 新增用户
	 *
	 * @param vo 用户
	 * @return
	 */
	Boolean add(SsoUserVO vo);

	/**
	 * 删除用户VO
	 *
	 * @param clientId
	 * @return
	 */
	Boolean delete(String clientId);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @return
	 */
	IPage<SsoUserVO> selectPageVo(Page page);
}
