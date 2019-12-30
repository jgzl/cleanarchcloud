package com.dlihaifeng.conversion.platform.application.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dlihaifeng.conversion.platform.application.model.dao.OrderItemDAO;

/**
 * @author lihaifeng
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItemDAO> {
}
