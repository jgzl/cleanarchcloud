/*
 * Copyright (c) 2019-2019
 */

package com.gitee.application.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gitee.application.model.bo.OrderBO;
import com.gitee.application.model.dao.OrderDAO;
import com.gitee.application.model.dao.OrderItemDAO;
import com.gitee.application.model.vo.OrderVO;
import com.gitee.application.service.IOrderItemService;
import com.gitee.application.service.IOrderService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 */
@Slf4j
@RestController
@RequestMapping
@Api(value = "demo测试服务", tags = {"demo测试服务"})
public class IndexController {

  @Autowired
  private Environment environment;

  @Autowired
  private IOrderService orderService;

  @Autowired
  private IOrderItemService orderItemService;

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Value("${redis.topic.chat}")
  private String chatTopic;

  @GetMapping("ping")
  @ApiOperation(value = "存活心跳接口")
  public String ping() {
    log.info("ping_url:{}", environment.getProperty("server.port"));
    return environment.getProperty("server.port");
  }

  /**
   * 无参创建订单
   * @return
   */
  @GetMapping("/orderWithNoArgs")
  @ApiOperation(value = "无参创建订单")
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
  @GetMapping("/createOrders")
  @ApiOperation(value = "通过OrderVO创建订单DAO")
  public List<OrderDAO> createOrders(@Valid OrderVO orderVO) {
    List<OrderBO> orderVOs = orderService.createOrderByVO(orderVO);
    orderVOs.forEach(System.out::println);
    List<OrderDAO> orderDAOs = orderService.query().eq("shop_id", 2).list();
    orderDAOs.forEach(System.out::println);
    return orderDAOs;
  }

  /**
   * 发送消息
   * 先登录 http://www.websocket-test.com/ 打开三个窗口输入
   * ws://127.0.0.1:8020/ws?token=lihaifeng1
   * ws://127.0.0.1:8020/ws?token=lihaifeng2
   * ws://127.0.0.1:8020/ws?token=lihaifeng3
   * 最后再调用这个方法，
   * 发送 http://127.0.0.1:8020/sendMsg?msg=%E7%BE%A4%E5%8F%91WebSocket%E6%B6%88%E6%81%AF
   * 发送 http://127.0.0.1:8020/sendMsg?msg=%E7%BE%A4%E5%8F%91WebSocket%E6%B6%88%E6%81%AF
   * 如果没有名字李海峰,则
   * @param msg
   * @return
   */
  @GetMapping("/sendMsg")
  @ApiOperation(value = "发送信息")
  public String sendMsg(String msg) {
    redisTemplate.convertAndSend(chatTopic, msg);
    return "success";
  }

  /**
   * 上传文件
   * @param multipartFile
   * @param request
   * @param response
   */
  @SneakyThrows
  @PostMapping("/upload")
  public void upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request,
      HttpServletResponse response) {
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
        "attachment;fileName=" + java.net.URLEncoder.encode(multipartFile.getOriginalFilename(), "UTF-8"));
    response.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
    response.setCharacterEncoding("UTF-8");
    IOUtils.copy(multipartFile.getInputStream(), response.getOutputStream());
  }
}
