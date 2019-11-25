/*
 * Copyright (c) 2019-2019
 */

package com.dlihaifeng.conversion.platform.application.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlihaifeng.conversion.platform.application.model.bo.OrderBO;
import com.dlihaifeng.conversion.platform.application.model.dao.OrderDAO;
import com.dlihaifeng.conversion.platform.application.model.dao.OrderItemDAO;
import com.dlihaifeng.conversion.platform.application.model.vo.OrderVO;
import com.dlihaifeng.conversion.platform.application.service.IOrderItemService;
import com.dlihaifeng.conversion.platform.application.service.IOrderService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 */
@Slf4j
@Controller
@ResponseBody
public class IndexController {

  @Autowired
  private Environment environment;

  @Autowired
  private IOrderService orderService;

  @Autowired
  private IOrderItemService orderItemService;

  @RequestMapping("ping")
  public String ping() {
    log.info("ping_url:{}", environment.getProperty("server.port"));
    return environment.getProperty("server.port");
  }

  /**
   * 🈚️参创建订单
   * @return
   */
  @RequestMapping("/orderWithNoArgs")
  public List createOrderWithNoArguments() {
    List<OrderDAO> orders = Lists.newArrayListWithCapacity(1300);
    List<OrderItemDAO> orderItems = Lists.newArrayListWithCapacity(1300);
    for (long i = 0; i < 1000; i++) {
      OrderDAO orderDAO = new OrderDAO();
      orderDAO.setCode("a0" + i);
      orderDAO.setShopId(i % 8);
      orderDAO.setCreateTime(new Date());
      orders.add(orderDAO);
    }
    orderService.saveBatch(orders);
    orders.forEach(s -> {
      OrderItemDAO orderItemDAO = new OrderItemDAO();
      orderItemDAO.setOrderId(s.getId());
      orderItemDAO.setShopId(s.getShopId());
      orderItemDAO.setName(s.getId() + ":name");
      orderItems.add(orderItemDAO);
    });
    orderItemService.saveBatch(orderItems);
    List<OrderDAO> shopIds = orderService.query().eq("shop_id", 2).list();
    return shopIds;
  }

  /**
   * 通过orderVO视图对象创建订单对象
   * @param orderVO
   * @return
   */
  @RequestMapping("/createOrders")
  public List<OrderDAO> createOrders(@Valid OrderVO orderVO) {
    List<OrderBO> orderVOs = orderService.createOrderByVO(orderVO);
    orderVOs.forEach(System.out::println);
    List<OrderDAO> orderDAOs = orderService.query().eq("shop_id", 2).list();
    orderDAOs.forEach(System.out::println);
    return orderDAOs;
  }
}
