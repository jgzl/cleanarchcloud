package com.gitee.application.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.application.mapper.OrderMapper;
import com.gitee.application.model.bo.OrderBO;
import com.gitee.application.model.convertor.OrderConvertor;
import com.gitee.application.model.dao.OrderDAO;
import com.gitee.application.model.vo.OrderVO;
import com.gitee.application.service.IOrderService;
import com.google.common.collect.Lists;

/**
 * @author lihaifeng
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDAO> implements IOrderService {
  @Transactional(rollbackFor = Exception.class)
  @Override
  public List<OrderBO> createOrderByVO(OrderVO orderVO) {
    OrderDAO orderDAO = OrderConvertor.INSTANCE.covertVO2DAO(orderVO);
    orderDAO.setCreateTime(new Date());
    this.baseMapper.insert(orderDAO);
    List<OrderBO> orderBOs = Lists.newArrayListWithCapacity(1);
    orderBOs.add(OrderConvertor.INSTANCE.convertDAO2BO(orderDAO));
    return orderBOs;
  }
}
