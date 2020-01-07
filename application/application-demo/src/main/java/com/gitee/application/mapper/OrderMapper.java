package com.gitee.application.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gitee.application.model.bo.OrderBO;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.application.model.dao.OrderDAO;

import java.util.List;
import java.util.Map;

/**
 * @author lihaifeng
 */
//@Mapper
public interface OrderMapper extends BaseMapper<OrderDAO> {

    List<Map<Object,Object>> list();
}
