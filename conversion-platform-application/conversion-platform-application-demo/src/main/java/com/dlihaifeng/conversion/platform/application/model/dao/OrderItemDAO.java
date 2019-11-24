package com.dlihaifeng.conversion.platform.application.model.dao;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
@TableName("o_order_item")
public class OrderItemDAO implements Serializable {
  @TableId
  private Long id;

  private Long shopId;

  private Long orderId;

  private String name;
}
