package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.github.jgzl.common.core.model.Entity;
import com.github.jgzl.common.core.util.StringUtils;
import com.github.jgzl.common.data.TenantEnvironment;
import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.infra.upms.domain.dto.ResourceQueryDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Resource;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Role;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.RoleRes;
import com.github.jgzl.infra.upms.domain.enums.ResourceType;
import com.github.jgzl.infra.upms.domain.vo.VueRouter;
import com.github.jgzl.infra.upms.repository.ResourceMapper;
import com.github.jgzl.infra.upms.repository.RoleMapper;
import com.github.jgzl.infra.upms.repository.RoleResMapper;
import com.github.jgzl.infra.upms.service.ResourceService;
import com.github.jgzl.infra.upms.service.RoleResService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * <p>
 * 业务实现类
 * 资源
 * </p>
 *
 * @author Levin
 * @since 2019-07-03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl extends SuperServiceImpl<ResourceMapper, Resource> implements ResourceService {

    private final RoleMapper roleMapper;
    private final RoleResService roleResService;
    private final RoleResMapper roleResMapper;
    private final TenantEnvironment tenantEnvironment;

    @Override
    public List<VueRouter> findVisibleResource(ResourceQueryDTO resource) {
        return baseMapper.findVisibleResource(resource);
    }

    public static final String DEFAULT_PATH = "/system/development/release/tenant_%s";
    public static final String DEFAULT_COMPONENT = "/system/development/build/standard";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addResource(Resource resource) {
        if (ResourceType.BUILD_PUBLISH.eq(resource.getType())) {
            resource.setPath(String.format(DEFAULT_PATH, tenantEnvironment.tenantId()) + "/" + resource.getModel());
            resource.setComponent(DEFAULT_COMPONENT);
        }
        final String treePath = this.baseMapper.getTreePathByParentId(resource.getParentId());
        if (StringUtils.isNotBlank(treePath)) {
            resource.setTreePath(treePath);
        }
        this.baseMapper.insert(resource);
        final List<Role> roles = this.roleMapper.selectList(Wraps.<Role>lbQ().eq(Role::getSuperRole, true)
                .eq(Role::getLocked, false));
        if (CollUtil.isEmpty(roles)) {
            return;
        }
        // 给管理员角色挂载权限
        final List<RoleRes> resList = roles.stream().map(role -> RoleRes.builder()
                .roleId(role.getId()).resId(resource.getId()).build()).collect(toList());
        roleResService.saveBatch(resList);
    }

    @Override
    public void editResourceById(Resource resource) {
        if (ResourceType.BUILD_PUBLISH.eq(resource.getType())) {
            resource.setPath(String.format(DEFAULT_PATH, tenantEnvironment.tenantId()) + "/" + resource.getModel());
            resource.setComponent(DEFAULT_COMPONENT);
        }
        this.baseMapper.updateById(resource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delResource(Long resourceId) {
        List<Long> resourceIds = Lists.newArrayList(resourceId);
        final List<Resource> children = this.baseMapper.findChildrenById(resourceId);
        if (CollectionUtil.isNotEmpty(children)) {
            resourceIds.addAll(children.stream().map(Entity::getId).collect(toList()));
        }
        // 删除菜单和按钮
        this.baseMapper.deleteBatchIds(resourceIds);
        // 删除对应的资源权限
        this.roleResMapper.delete(Wraps.<RoleRes>lbQ().in(RoleRes::getResId, resourceIds));
    }
}
