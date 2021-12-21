package com.github.jgzl.infra.codegen.mapper;

import com.github.jgzl.common.data.datascope.ExtendBaseMapper;
import com.github.jgzl.infra.codegen.entity.GenDatasourceConf;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源表
 *
 * @author lihaifeng
 * @date 2019-03-31 16:00:20
 */
@Mapper
public interface GenDatasourceConfMapper extends ExtendBaseMapper<GenDatasourceConf> {

}
