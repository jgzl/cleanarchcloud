package com.dlihaifeng.conversion.platform.application.model.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
public class OrderVO {

  private Long orderId;

  @NotNull(message = "商铺不允许为空")
  private Long shopId;

  @NotNull(message = "商品code不允许为空")
  private String code;
}
