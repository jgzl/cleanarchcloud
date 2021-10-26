package com.github.jgzl.infra.upms.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jgzl.common.api.dataobject.SysUser;
import com.github.jgzl.infra.upms.dataobject.SysSocialUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 客户端dao
 *
 * @author lihaifeng
 * 2019/7/5 14:49
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

	@Select("SELECT " +
			"    su.* " +
			"FROM " +
			"    `sys_social_user_auth` sua " +
			"INNER JOIN `sys_social_user` ssu ON sua.social_user_id = ssu.id " +
			"INNER JOIN `sys_user` su ON sua.user_id = su.id " +
			"WHERE " +
			"    ssu.uuid = #{uuid} " +
			"    AND ssu.source = #{source}")
	List<SysUser> findUserBySocialUserUuidAndSource(@Param("uuid") String uuid,@Param("source") String source);
}
