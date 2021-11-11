package com.github.jgzl.infra.upms.service;

import com.github.jgzl.common.data.mybatis.SuperService;
import com.github.jgzl.infra.upms.domain.entity.common.AreaEntity;

import java.util.List;

/**
 * @author Levin
 */
public interface AreaService extends SuperService<AreaEntity> {

    /**
     * 根据 parentId 查询数据集
     *
     * @param parentId parentId
     * @return 查询结果
     */
    List<AreaEntity> listArea(Integer parentId);

    /**
     * 保存或者修改地区
     *
     * @param area area
     */
    void saveOrUpdateArea(AreaEntity area);

}
