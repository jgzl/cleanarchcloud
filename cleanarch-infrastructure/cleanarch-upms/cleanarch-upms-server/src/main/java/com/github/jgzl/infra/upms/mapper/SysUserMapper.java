package com.github.jgzl.infra.upms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.data.datascope.DataScope;
import com.github.jgzl.common.data.datascope.ExtendBaseMapper;
import com.github.jgzl.infra.upms.api.dto.UserDTO;
import com.github.jgzl.infra.upms.api.entity.SysUser;
import com.github.jgzl.infra.upms.api.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lihaifeng
 * @since 2017-10-29
 */
@Mapper
public interface SysUserMapper extends ExtendBaseMapper<SysUser> {

	/**
	 * 通过用户名查询用户信息（含有角色信息）
	 *
	 * @param username 用户名
	 * @return userVo
	 */
	UserVO getUserVoByUsername(String username);

	/**
	 * 分页查询用户信息（含角色）
	 *
	 * @param page      分页
	 * @param userDTO   查询参数
	 * @param dataScope
	 * @return list
	 */
	IPage<UserVO> getUserVosPage(Page page, @Param("query") UserDTO userDTO, DataScope dataScope);

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return userVo
	 */
	UserVO getUserVoById(Integer id);

	/**
	 * 查询用户列表
	 *
	 * @param userDTO   查询条件
	 * @param dataScope 数据权限声明
	 * @return
	 */
	List<UserVO> selectVoListByScope(@Param("query") UserDTO userDTO, DataScope dataScope);

}
