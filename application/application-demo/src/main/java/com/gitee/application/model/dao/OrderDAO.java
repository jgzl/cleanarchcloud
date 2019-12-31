package com.gitee.application.model.dao;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
@TableName("o_order")
public class OrderDAO implements Serializable {
  @TableId
  private Long id;

  private Long shopId;

  private String code;

  private Date createTime;
}
