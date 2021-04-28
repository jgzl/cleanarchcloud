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

package com.gitee.application.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
		OrderDO orderDO = OrderConvertor.INSTANCE.covertVO2DO(orderVO);
		orderDO.setCreateTime(new Date());
		this.baseMapper.insert(orderDO);
		List<OrderBO> orderBOs = Lists.newArrayListWithCapacity(1);
		orderBOs.add(OrderConvertor.INSTANCE.convertDO2BO(orderDO));
		return orderBOs;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<OrderBO> pageList() {
		IPage<OrderDO> page = new Page<>(1, 2);
		QueryWrapper<OrderDO> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("code", "123");
		IPage<OrderDO> orderDOIPage = this.baseMapper.selectPage(page, queryWrapper);
		this.baseMapper.selectPage(page, new LambdaQueryWrapper<OrderDO>().eq(OrderDO::getCode, "123"));
		System.out.println(orderDOIPage.getPages());
		System.out.println(orderDOIPage.getTotal());
		orderDOIPage.getRecords().forEach(a -> System.out.println(a));
		return null;
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insert() {
		OrderDO orderDO = new OrderDO();
		orderDO.setCode("123");
		this.save(orderDO);
	}
}
