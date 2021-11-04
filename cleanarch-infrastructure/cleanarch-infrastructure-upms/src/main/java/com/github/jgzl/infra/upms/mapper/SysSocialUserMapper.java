package com.github.jgzl.infra.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jgzl.infra.upms.dataobject.SysSocialUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 社会登录用户管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Mapper
public interface SysSocialUserMapper extends BaseMapper<SysSocialUser> {

	@Select("SELECT " +
			"    su.* " +
			"FROM " +
			"    `sys_social_user_auth` sua " +
			"INNER JOIN `sys_social_user` su ON sua.social_user_id = su.id " +
			"WHERE " +
			"    sua.user_id = #{userId} " +
			"    AND su.source = #{source}")
	public List<SysSocialUser> findSocialUserByUserIdAndSource(@Param("userId") String userId,@Param("source") String source);
}