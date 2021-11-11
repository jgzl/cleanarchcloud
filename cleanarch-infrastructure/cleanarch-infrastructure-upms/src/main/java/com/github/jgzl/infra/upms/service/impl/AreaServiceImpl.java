package com.github.jgzl.infra.upms.service.impl;

import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.infra.upms.domain.entity.common.AreaEntity;
import com.github.jgzl.infra.upms.repository.AreaMapper;
import com.github.jgzl.infra.upms.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Levin
 */
@Service
@RequiredArgsConstructor
public class AreaServiceImpl extends SuperServiceImpl<AreaMapper, AreaEntity> implements AreaService {

    @Override
    public List<AreaEntity> listArea(Integer parentId) {
        return baseMapper.listArea(parentId);
    }

    @Override
    public void saveOrUpdateArea(AreaEntity area) {
        final long count = count(Wraps.<AreaEntity>lbQ().eq(AreaEntity::getId, area.getId()));
        if (count == 0) {
            baseMapper.insert(area);
        } else {
            baseMapper.updateById(area);
        }
    }


}
