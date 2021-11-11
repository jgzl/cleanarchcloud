package com.github.jgzl.infra.upms.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.common.data.mybatis.auth.DataScope;
import com.github.jgzl.common.data.mybatis.conditions.query.LbqWrapper;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.User;
import com.github.jgzl.infra.upms.domain.vo.UserResp;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Levin
 */
@DynamicDS
@Repository
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
}
