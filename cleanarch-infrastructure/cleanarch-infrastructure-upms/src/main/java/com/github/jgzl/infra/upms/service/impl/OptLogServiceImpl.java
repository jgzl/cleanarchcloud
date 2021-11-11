package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.github.jgzl.common.api.vo.OptLogDTO;
import com.github.jgzl.common.core.util.RegionUtils;
import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.infra.upms.domain.entity.log.OptLog;
import com.github.jgzl.infra.upms.repository.OptLogMapper;
import com.github.jgzl.infra.upms.service.OptLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Levin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OptLogServiceImpl extends SuperServiceImpl<OptLogMapper, OptLog> implements OptLogService {

    private final OptLogMapper optLogMapper;

    @Override
    public void save(OptLogDTO dto) {
        DynamicDataSourceContextHolder.push(dto.getDsKey());
        log.info("[日志信息] - {}", JSON.toJSONString(dto));
        final OptLog record = BeanUtil.toBean(dto, OptLog.class);
        record.setLocation(RegionUtils.getRegion(dto.getIp()));
        this.optLogMapper.insert(record);
        DynamicDataSourceContextHolder.poll();
    }

}
