package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.infra.upms.domain.dto.RoleResSaveDTO;
import com.github.jgzl.infra.upms.domain.dto.UserRoleSaveDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.RoleRes;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.UserRole;
import com.github.jgzl.infra.upms.domain.vo.RoleResMenuMapperResp;
import com.github.jgzl.infra.upms.domain.vo.RoleResVO;
import com.github.jgzl.infra.upms.repository.RoleResMapper;
import com.github.jgzl.infra.upms.service.RoleResService;
import com.github.jgzl.infra.upms.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 业务实现类
 * 角色的资源
 * </p>
 *
 * @author lihaifeng
 * @since 2019-07-03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleResServiceImpl extends SuperServiceImpl<RoleResMapper, RoleRes> implements RoleResService {

    private final UserRoleService userRoleService;

    @Override
    public RoleResVO findAuthorityIdByRoleId(Long roleId) {
        final List<RoleResMenuMapperResp> list = this.baseMapper.selectRoleResByRoleId(roleId);
        List<Long> menuIdList = list.stream().filter(xx -> xx.getType() == 1 || xx.getType() == 5)
                .mapToLong(RoleResMenuMapperResp::getId).boxed().distinct().collect(Collectors.toList());
        List<Long> resourceIdList = list.stream().filter(xx -> xx.getType() == 2)
                .mapToLong(RoleResMenuMapperResp::getId).boxed().distinct().collect(Collectors.toList());
        return RoleResVO.builder()
                .menuIdList(menuIdList)
                .resourceIdList(resourceIdList)
                .build();
    }


    @Override
    public boolean saveUserRole(UserRoleSaveDTO userRole) {
        userRoleService.remove(Wraps.<UserRole>lbQ().eq(UserRole::getRoleId, userRole.getRoleId()));
        List<UserRole> list = userRole.getUserIdList()
                .stream()
                .map((userId) -> UserRole.builder()
                        .userId(userId)
                        .roleId(userRole.getRoleId())
                        .build())
                .collect(Collectors.toList());
        userRoleService.saveBatch(list);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleAuthority(RoleResSaveDTO dto) {
        //删除角色和资源的关联
        super.remove(Wraps.<RoleRes>lbQ().eq(RoleRes::getRoleId, dto.getRoleId()));
        resHandler(dto, dto.getRoleId());
    }

    private void resHandler(RoleResSaveDTO data, Long roleId) {
        final Set<Long> set = data.getResIds();
        if (CollectionUtil.isEmpty(set)) {
            return;
        }
        final List<RoleRes> roleRes = set.stream().filter(Objects::nonNull)
                .map(resId -> RoleRes.builder().resId(resId).roleId(roleId).build())
                .collect(toList());
        super.saveBatch(roleRes);
    }
}
