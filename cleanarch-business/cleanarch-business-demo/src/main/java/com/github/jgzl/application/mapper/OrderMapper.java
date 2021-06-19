package com.github.jgzl.application.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jgzl.application.model.dao.OrderDo;

/**
 * @author lihaifeng
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDo> {

	List<Map<Object, Object>> list();
}
