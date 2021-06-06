package com.github.jgzl.application.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.common.security.dataobject.SysUserDO;
import com.github.jgzl.common.security.vo.SysUserVO;
import com.github.jgzl.common.security.vo.UserVO;

/**
 * @author Administrator
 */
public interface SysUserService extends IService<SysUserDO> {
	UserVO findUserByUsername(String username);

	UserVO findUserByMobile(String mobile);

	/**
	 * 查询用户VO
	 *
	 * @param id
	 * @return
	 */
	SysUserVO getVo(String id);

	/**
	 * 更新用户
	 *
	 * @param vo 用户
	 * @return
	 */
	Boolean update(SysUserVO vo);

	/**
	 * 新增用户
	 *
	 * @param vo 用户
	 * @return
	 */
	Boolean add(SysUserVO vo);

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
	IPage<SysUserVO> selectPageVo(Page page);
}
