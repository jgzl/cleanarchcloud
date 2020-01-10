package com.gitee.application.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.application.model.bo.OrderBO;
import com.gitee.application.model.dao.OrderDO;
import com.gitee.application.model.vo.OrderVO;

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
