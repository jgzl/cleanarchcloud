package com.github.jgzl.infra.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jgzl.common.api.dataobject.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 日志表 Mapper 接口
 * </p>
 *
 * @author lengleng
 * @since 2017-11-20
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

}
