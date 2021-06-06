package com.github.jgzl.application.model.bo;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
public class OrderBO {
	private Long orderId;

	private Long shopId;

	private String code;

	private Date createTime;

	private List<OrderItemBO> orderItems;
}
