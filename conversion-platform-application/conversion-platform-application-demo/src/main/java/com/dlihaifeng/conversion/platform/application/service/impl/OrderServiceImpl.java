package com.dlihaifeng.conversion.platform.application.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlihaifeng.conversion.platform.application.mapper.OrderMapper;
import com.dlihaifeng.conversion.platform.application.model.dao.OrderDAO;
import com.dlihaifeng.conversion.platform.application.service.IOrderService;

/**
 * @author lihaifeng
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDAO> implements IOrderService {

}
