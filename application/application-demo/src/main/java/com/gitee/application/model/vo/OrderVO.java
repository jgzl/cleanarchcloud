package com.gitee.application.model.vo;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
@ApiModel(value = "订单视图对象")
public class OrderVO {

	@ApiModelProperty(value = "订单Id", allowEmptyValue = true)
	private Long orderId;

	@ApiModelProperty(value = "商铺Id", allowableValues = "1")
	@NotNull(message = "商铺不允许为空")
	private Long shopId;

	@ApiModelProperty(value = "商品code", allowableValues = "AAA")
	@NotNull(message = "商品code不允许为空")
	private String code;
}
