package com.github.jgzl.infra.upms.repository;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.common.data.mybatis.auth.DataScope;
import com.github.jgzl.common.data.mybatis.conditions.query.LbqWrapper;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.User;
import com.github.jgzl.infra.upms.domain.vo.UserResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * @author lihaifeng
 */
@DynamicDS
@Mapper
public interface UserMapper extends SuperMapper<User> {

    /**
     * 分页查询用户
     *
     * @param page    page
     * @param wrapper wrapper
     * @return 查询结果
     */
    IPage<UserResp> findPage(IPage<User> page, LbqWrapper<User> wrapper);
    /**
     * 带数据权限用户列表
     *
     * @param dataScope dataScopeAspectJExpressionPointcut
     * @return 用户
     */
    List<User> list(DataScope dataScope);
	@Select("SELECT " +
			"    su.* " +
			"FROM " +
			"    `sys_social_user_auth` sua " +
			"INNER JOIN `sys_social_user` ssu ON sua.social_user_id = ssu.id " +
			"INNER JOIN `t_user` su ON sua.id = su.id " +
			"WHERE " +
			"    ssu.uuid = #{uuid} " +
			"    AND ssu.source = #{source}")
	List<User> findUserBySocialUserUuidAndSource(@Param("uuid") String uuid, @Param("source") String source);
}
