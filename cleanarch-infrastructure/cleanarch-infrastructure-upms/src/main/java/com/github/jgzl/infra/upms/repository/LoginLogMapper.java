package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.log.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 系统日志
 * </p>
 *
 * @author lihaifeng
 * @since 2019-10-20
 */
@DynamicDS
@Mapper
public interface LoginLogMapper extends SuperMapper<LoginLog> {


    /**
     * 统计 IP 数据
     *
     * @return 统计结果
     */
    @Select("SELECT count(DISTINCT ( ip )) FROM common_login_log")
    long countDistinctLoginIp();

}
