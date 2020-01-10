package com.gitee.application.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.application.mapper.OrderMapper;
import com.gitee.application.model.bo.OrderBO;
import com.gitee.application.model.convertor.OrderConvertor;
import com.gitee.application.model.dao.OrderDO;
import com.gitee.application.model.vo.OrderVO;
import com.gitee.application.service.IOrderService;
import com.google.common.collect.Lists;

/**
 * @author lihaifeng
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements IOrderService {

  @Transactional(rollbackFor = Exception.class)
  @Override
  public List<OrderBO> createOrderByVO(OrderVO orderVO) {
    OrderDO orderDAO = OrderConvertor.INSTANCE.covertVO2DAO(orderVO);
    orderDAO.setCreateTime(new Date());
    this.baseMapper.insert(orderDAO);
    List<OrderBO> orderBOs = Lists.newArrayListWithCapacity(1);
    orderBOs.add(OrderConvertor.INSTANCE.convertDAO2BO(orderDAO));
    return orderBOs;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public List<OrderBO> pageList() {
      IPage<OrderDO> page = new Page<>(1, 2);
      QueryWrapper<OrderDO> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("code", "123");
      IPage<OrderDO> orderDAOIPage = this.baseMapper.selectPage(page, queryWrapper);
      this.baseMapper.selectPage(page, new LambdaQueryWrapper<OrderDO>().eq(OrderDO::getCode, "123"));
      System.out.println(orderDAOIPage.getPages());
      System.out.println(orderDAOIPage.getTotal());
      orderDAOIPage.getRecords().forEach(a -> System.out.println(a));
      return null;
  }


  @Transactional(rollbackFor = Exception.class)
  @Override
  public void insert(){
      OrderDO orderDAO = new OrderDO();
      orderDAO.setCode("123");
      this.save(orderDAO);
  }

}
