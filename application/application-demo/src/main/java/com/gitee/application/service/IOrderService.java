package com.gitee.application.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.application.model.bo.OrderBO;
import com.gitee.application.model.dao.OrderDAO;
import com.gitee.application.model.vo.OrderVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lihaifeng
 */
public interface IOrderService extends IService<OrderDAO> {
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
