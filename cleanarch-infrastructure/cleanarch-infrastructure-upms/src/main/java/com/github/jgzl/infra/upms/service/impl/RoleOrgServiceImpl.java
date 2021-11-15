package com.github.jgzl.infra.upms.service.impl;
import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.RoleOrg;
import com.github.jgzl.infra.upms.repository.RoleOrgMapper;
import com.github.jgzl.infra.upms.service.RoleOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
/**
 * <p>
 * 业务实现类
 * 角色组织关系
 * </p>
 *
 * @author Levin
 * @since 2019-07-03
 */
@Slf4j
@Service
public class RoleOrgServiceImpl extends SuperServiceImpl<RoleOrgMapper, RoleOrg> implements RoleOrgService {
    @Override
    public List<Long> listOrgByRoleId(Long id) {
        List<RoleOrg> list = super.list(Wraps.<RoleOrg>lbQ().eq(RoleOrg::getRoleId, id));
        List<Long> orgList = list.stream().mapToLong(RoleOrg::getOrgId).boxed().collect(Collectors.toList());
        return orgList;
    }
}
