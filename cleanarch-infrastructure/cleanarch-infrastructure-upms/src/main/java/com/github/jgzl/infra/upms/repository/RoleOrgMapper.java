package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.RoleOrg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author lihaifeng
 */
@DynamicDS
@Mapper
public interface RoleOrgMapper extends SuperMapper<RoleOrg> {

}
