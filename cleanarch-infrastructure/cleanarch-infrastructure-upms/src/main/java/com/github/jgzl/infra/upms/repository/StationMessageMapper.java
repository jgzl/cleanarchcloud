package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.message.StationMessage;
import org.springframework.stereotype.Repository;

/**
 * @author Levin
 */
@Repository
public interface StationMessageMapper extends SuperMapper<StationMessage> {

}
