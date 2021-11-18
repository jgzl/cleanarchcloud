package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.common.data.mybatis.auth.DataScope;
import com.github.jgzl.common.data.mybatis.auth.DataScopeType;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.mybatis.conditions.query.LbqWrapper;
import com.github.jgzl.common.data.page.PageRequest;
import com.github.jgzl.infra.upms.domain.dto.StationPageDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Station;
import com.github.jgzl.infra.upms.repository.StationMapper;
import com.github.jgzl.infra.upms.service.StationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 业务实现类
 * 岗位
 * </p>
 *
 * @author lihaifeng
 * @since 2019-07-22
 */
@Slf4j
@Service
public class StationServiceImpl extends SuperServiceImpl<StationMapper, Station> implements StationService {


    @Override
    public IPage<Station> findStationPage(PageRequest params, StationPageDTO data) {
        Station station = BeanUtil.toBean(data, Station.class);
        final LbqWrapper<Station> wrapper = Wraps.<Station>lbQ().like(Station::getName, station.getName())
                .like(Station::getDescription, station.getDescription()).eq(Station::getType,data.getType())
                .eq(Station::getStatus,data.getStatus()).eq(Station::getOrgId, station.getOrgId())
                .eq(Station::getStatus, station.getStatus()).orderByAsc(Station::getSequence);
        return baseMapper.findStationPage(params.buildPage(), wrapper, DataScope.builder().scopeType(DataScopeType.ALL).build());
    }
}
