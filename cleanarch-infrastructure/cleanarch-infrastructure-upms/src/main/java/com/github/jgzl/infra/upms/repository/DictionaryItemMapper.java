package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.common.DictionaryItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 字典项
 * </p>
 *
 * @author Levin
 * @date 2019-07-02
 */
@Mapper
public interface DictionaryItemMapper extends SuperMapper<DictionaryItem> {

}
