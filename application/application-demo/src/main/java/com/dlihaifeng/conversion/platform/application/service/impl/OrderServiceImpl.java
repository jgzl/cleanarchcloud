package com.dlihaifeng.conversion.platform.application.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlihaifeng.conversion.platform.application.mapper.OrderMapper;
import com.dlihaifeng.conversion.platform.application.model.bo.OrderBO;
import com.dlihaifeng.conversion.platform.application.model.convertor.OrderConvertor;
import com.dlihaifeng.conversion.platform.application.model.dao.OrderDAO;
import com.dlihaifeng.conversion.platform.application.model.vo.OrderVO;
import com.dlihaifeng.conversion.platform.application.service.IOrderService;
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
