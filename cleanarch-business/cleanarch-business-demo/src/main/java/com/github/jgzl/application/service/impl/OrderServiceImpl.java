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

package com.github.jgzl.application.service.impl;

import java.util.Date;
import java.util.List;

import com.github.jgzl.application.mapper.OrderMapper;
import com.github.jgzl.application.model.bo.OrderBO;
import com.github.jgzl.application.model.convertor.OrderConvertor;
import com.github.jgzl.application.model.dao.OrderDo;
import com.github.jgzl.application.model.vo.OrderVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.application.service.IOrderService;
import com.google.common.collect.Lists;

/**
 * @author lihaifeng
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDo> implements IOrderService {

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<OrderBO> createOrderByVo(OrderVo orderVo) {
		OrderDo orderDo = OrderConvertor.INSTANCE.covertVo2Do(orderVo);
		orderDo.setCreateTime(new Date());
		this.baseMapper.insert(orderDo);
		List<OrderBO> orderBOs = Lists.newArrayListWithCapacity(1);
		orderBOs.add(OrderConvertor.INSTANCE.convertDo2BO(orderDo));
		return orderBOs;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<OrderBO> pageList() {
		IPage<OrderDo> page = new Page<>(1, 2);
		QueryWrapper<OrderDo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("code", "123");
		IPage<OrderDo> orderDoIPage = this.baseMapper.selectPage(page, queryWrapper);
		this.baseMapper.selectPage(page, new LambdaQueryWrapper<OrderDo>().eq(OrderDo::getCode, "123"));
		System.out.println(orderDoIPage.getPages());
		System.out.println(orderDoIPage.getTotal());
		orderDoIPage.getRecords().forEach(System.out::println);
		return null;
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insert() {
		OrderDo orderDo = new OrderDo();
		orderDo.setCode("123");
		this.save(orderDo);
	}
}
