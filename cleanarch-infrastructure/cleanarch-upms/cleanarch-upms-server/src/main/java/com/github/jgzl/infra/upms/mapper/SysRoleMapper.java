package com.github.jgzl.infra.upms.mapper;

import com.github.jgzl.common.data.datascope.ExtendBaseMapper;
import com.github.jgzl.infra.upms.api.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lihaifeng
 * @since 2017-10-29
 */
@Mapper
public interface SysRoleMapper extends ExtendBaseMapper<SysRole> {

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	List<SysRole> listRolesByUserId(Integer userId);

}
