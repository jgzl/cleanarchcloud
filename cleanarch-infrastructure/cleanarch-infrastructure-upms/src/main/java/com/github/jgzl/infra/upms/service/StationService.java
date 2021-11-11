package com.github.jgzl.infra.upms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.jgzl.common.data.mybatis.SuperService;
import com.github.jgzl.common.data.page.PageRequest;
import com.github.jgzl.infra.upms.domain.dto.StationPageDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Station;

/**
 * <p>
 * 业务接口
 * 岗位
 * </p>
 *
 * @author Levin
 * @since 2019-07-22
 */
public interface StationService extends SuperService<Station> {
    /**
     * 按权限查询岗位的分页信息
     *
     * @param params params
     * @param data data
     * @return Station
     */
    IPage<Station> findStationPage(PageRequest params, StationPageDTO data);
}
