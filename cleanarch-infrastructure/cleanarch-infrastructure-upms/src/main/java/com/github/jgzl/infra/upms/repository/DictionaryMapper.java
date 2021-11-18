package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.common.Dictionary;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 字典类型
 * </p>
 *
 * @author lihaifeng
 * @date 2019-07-02
 */
@DynamicDS
@Mapper
public interface DictionaryMapper extends SuperMapper<Dictionary> {

}
