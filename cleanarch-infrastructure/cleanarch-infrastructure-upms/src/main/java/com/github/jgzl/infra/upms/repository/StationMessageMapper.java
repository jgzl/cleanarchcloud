package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.message.StationMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Levin
 */
@Mapper
public interface StationMessageMapper extends SuperMapper<StationMessage> {

}
