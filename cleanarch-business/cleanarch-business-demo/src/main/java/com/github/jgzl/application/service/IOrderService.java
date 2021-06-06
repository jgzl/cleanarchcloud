package com.github.jgzl.application.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.application.model.bo.OrderBO;
import com.github.jgzl.application.model.dao.OrderDO;
import com.github.jgzl.application.model.vo.OrderVO;

/**
 * @author lihaifeng
 */
public interface IOrderService extends IService<OrderDO> {
	/**
	 * 通过VO创建订单
	 *
	 * @param orderVO
	 * @return
	 */
	List<OrderBO> createOrderByVO(OrderVO orderVO);

	List<OrderBO> pageList();

	void insert();
}
