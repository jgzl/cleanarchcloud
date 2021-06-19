package com.github.jgzl.application.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.application.model.bo.OrderBO;
import com.github.jgzl.application.model.dao.OrderDo;
import com.github.jgzl.application.model.vo.OrderVo;

/**
 * @author lihaifeng
 */
public interface IOrderService extends IService<OrderDo> {
	/**
	 * 通过Vo创建订单
	 *
	 * @param orderVo
	 * @return
	 */
	List<OrderBO> createOrderByVo(OrderVo orderVo);

	List<OrderBO> pageList();

	void insert();
}
