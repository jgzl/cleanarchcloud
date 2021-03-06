package com.github.jgzl.application.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.common.security.dataobject.SysUserDo;
import com.github.jgzl.common.security.vo.SysUserVo;
import com.github.jgzl.common.security.vo.UserVo;

/**
 * @author Administrator
 */
public interface SysUserService extends IService<SysUserDo> {

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	UserVo findUserByUsername(String username);

	/**
	 * 根据用户名查找用户
	 * @param mobile
	 * @return
	 */
	UserVo findUserByMobile(String mobile);

	/**
	 * 根据用户名查找用户
	 * @param email
	 * @return
	 */
	UserVo findUserByEmail(String email);

	/**
	 * 查询用户Vo
	 *
	 * @param id
	 * @return
	 */
	SysUserVo getVo(String id);

	/**
	 * 更新用户
	 *
	 * @param vo 用户
	 * @return
	 */
	Boolean update(SysUserVo vo);

	/**
	 * 新增用户
	 *
	 * @param vo 用户
	 * @return
	 */
	Boolean add(SysUserVo vo);

	/**
	 * 删除用户Vo
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
	IPage<SysUserVo> selectPageVo(Page page);
}
