package com.github.jgzl.infra.upms.mapper;

import com.github.jgzl.common.data.datascope.ExtendBaseMapper;
import com.github.jgzl.infra.upms.api.entity.SysTenant;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租户
 *
 * @author lihaifeng
 * @date 2019-05-15 15:55:41
 */
@Mapper
public interface SysTenantMapper extends ExtendBaseMapper<SysTenant> {

}
