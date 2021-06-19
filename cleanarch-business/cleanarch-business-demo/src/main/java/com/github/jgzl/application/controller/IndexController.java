/*
 * Copyright [2020] [lihaifeng,xuhang]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.jgzl.application.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.github.jgzl.application.model.bo.OrderBO;
import com.github.jgzl.application.model.dao.OrderDo;
import com.github.jgzl.application.model.dao.OrderItemDo;
import com.github.jgzl.application.model.vo.OrderVo;
import com.github.jgzl.application.service.IOrderItemService;
import com.github.jgzl.application.service.IOrderService;
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
	public List createOrderWithNoArguments(HttpServletRequest request,HttpServletResponse response) {
		List<OrderDo> orders = Lists.newArrayListWithCapacity(1300);
		List<OrderItemDo> orderItems = Lists.newArrayListWithCapacity(1300);
		for (long i = 0; i < 1000; i++) {
			OrderDo orderDo = new OrderDo();
			orderDo.setCode("a0" + i);
			orderDo.setShopId(i % 8);
			orderDo.setCreateTime(new Date());
			orders.add(orderDo);
		}
		orderService.saveBatch(orders);
		orders.forEach(s -> {
			OrderItemDo orderItemDo = new OrderItemDo();
			orderItemDo.setOrderId(s.getId());
			orderItemDo.setShopId(s.getShopId());
			orderItemDo.setName(s.getId() + ":name");
			orderItems.add(orderItemDo);
		});
		orderItemService.saveBatch(orderItems);
		List<OrderDo> shopIds = orderService.query().eq("shop_id", 2).list();
		return shopIds;
	}

	/**
	 * 通过orderVo视图对象创建订单对象
	 * @param orderVo
	 * @return
	 */
	@GetMapping("/createOrders")
	@ApiOperation(value = "通过OrderVo创建订单Do")
	public List<OrderDo> createOrders(@Valid OrderVo orderVo) {
		List<OrderBO> orderVos = orderService.createOrderByVo(orderVo);
		orderVos.forEach(System.out::println);
		List<OrderDo> orderDos = orderService.query().eq("shop_id", 2).list();
		orderDos.forEach(System.out::println);
		return orderDos;
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
