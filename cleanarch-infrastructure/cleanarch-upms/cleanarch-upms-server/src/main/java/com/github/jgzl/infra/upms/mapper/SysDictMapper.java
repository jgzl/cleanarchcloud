package com.github.jgzl.infra.upms.mapper;

import com.github.jgzl.infra.upms.api.entity.SysDict;
import com.github.jgzl.common.data.datascope.ExtendBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author lihaifeng
 * @since 2017-11-19
 */
@Mapper
public interface SysDictMapper extends ExtendBaseMapper<SysDict> {

}
