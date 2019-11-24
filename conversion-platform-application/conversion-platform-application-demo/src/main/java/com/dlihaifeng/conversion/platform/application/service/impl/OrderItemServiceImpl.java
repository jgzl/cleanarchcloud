package com.dlihaifeng.conversion.platform.application.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlihaifeng.conversion.platform.application.mapper.OrderItemMapper;
import com.dlihaifeng.conversion.platform.application.model.dao.OrderItemDAO;
import com.dlihaifeng.conversion.platform.application.service.IOrderItemService;

/**
 * @author lihaifeng
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItemDAO> implements IOrderItemService {

}
