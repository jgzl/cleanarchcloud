package com.github.jgzl.infra.upms.mapper;

import com.github.jgzl.common.data.datascope.ExtendBaseMapper;
import com.github.jgzl.infra.upms.api.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 日志表 Mapper 接口
 * </p>
 *
 * @author lihaifeng
 * @since 2017-11-20
 */
@Mapper
public interface SysLogMapper extends ExtendBaseMapper<SysLog> {

}
