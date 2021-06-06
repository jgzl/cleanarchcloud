package com.gitee.application.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.application.model.dao.OrderDO;

/**
 * @author lihaifeng
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {

	List<Map<Object, Object>> list();
}
