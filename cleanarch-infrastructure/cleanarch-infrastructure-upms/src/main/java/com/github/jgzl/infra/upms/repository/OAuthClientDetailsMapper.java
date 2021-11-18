package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.OAuthClientDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lihaifeng
 */
@Mapper
public interface OAuthClientDetailsMapper extends SuperMapper<OAuthClientDetails> {


}
