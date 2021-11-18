package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.jgzl.common.core.util.StringUtils;
import com.github.jgzl.common.data.mybatis.auth.DataScope;
import com.github.jgzl.common.data.mybatis.auth.DataScopeService;
import com.github.jgzl.common.data.mybatis.auth.DataScopeType;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Org;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Role;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.RoleOrg;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.User;
import com.github.jgzl.infra.upms.repository.OrgMapper;
import com.github.jgzl.infra.upms.repository.RoleMapper;
import com.github.jgzl.infra.upms.repository.RoleOrgMapper;
import com.github.jgzl.infra.upms.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lihaifeng
 */
@Slf4j
@Service
public class DataScopeServiceImpl implements DataScopeService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleOrgMapper roleOrgMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private OrgMapper orgMapper;

    @Override
    public DataScope getDataScopeById(Long userId) {
        DataScope scope = new DataScope();
        List<Long> orgIds = new ArrayList<>();
        List<Role> list = roleMapper.findRoleByUserId(userId);
        if (CollectionUtils.isEmpty(list)) {
            return scope;
        }
        // 找到 dsType 最大的角色， dsType越大，角色拥有的权限最大
        Optional<Role> max = list.stream().max(Comparator.comparingInt((item) -> item.getScopeType().getVal()));
        if (!max.isPresent()) {
            return scope;
        }
        Role role = max.get();
        DataScopeType scopeType = role.getScopeType();
        scope.setScopeType(role.getScopeType());
        if (DataScopeType.CUSTOMIZE.eq(scopeType)) {
            List<RoleOrg> roleOrgList = roleOrgMapper.selectList(Wraps.<RoleOrg>lbQ()
                    .select(RoleOrg::getOrgId)
                    .eq(RoleOrg::getRoleId, role.getId()));
            orgIds = roleOrgList.stream().mapToLong(RoleOrg::getOrgId).boxed().collect(Collectors.toList());
        } else if (DataScopeType.THIS_LEVEL.eq(scopeType)) {
            User user = userMapper.selectById(userId);
            if (user != null && user.getOrgId() != null) {
                orgIds.add(user.getOrgId());
            }
        } else if (DataScopeType.THIS_LEVEL_CHILDREN.eq(scopeType)) {
            User user = userMapper.selectById(userId);
            if (user != null && user.getOrgId() != null) {
                List<Org> orgList = findChildren(Collections.singletonList(user.getOrgId()));
                if (CollectionUtil.isNotEmpty(orgList)) {
                    orgIds.addAll(orgList.stream().mapToLong(Org::getId).boxed().collect(Collectors.toList()));
                }
            }
        }
        scope.setOrgIds(orgIds);
        return scope;
    }

    public List<Org> findChildren(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        // MySQL 全文索引
        String applySql = String.format(" MATCH(tree_path) AGAINST('%s' IN BOOLEAN MODE) ", StringUtils.join(" ",ids));

        return orgMapper.selectList(Wraps.<Org>lbQ().in(Org::getId, ids).or(query -> query.apply(applySql)));
    }


}
