package com.gitee.application.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.application.model.dao.OrderDAO;

/**
 * @author lihaifeng
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDAO> {
}
