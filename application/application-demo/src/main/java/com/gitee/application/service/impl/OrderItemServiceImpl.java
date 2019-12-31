package com.gitee.application.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.application.mapper.OrderItemMapper;
import com.gitee.application.model.dao.OrderItemDAO;
import com.gitee.application.service.IOrderItemService;

/**
 * @author lihaifeng
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItemDAO> implements IOrderItemService {

}
