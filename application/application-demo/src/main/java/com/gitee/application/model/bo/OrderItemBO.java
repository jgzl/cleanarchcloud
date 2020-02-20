package com.gitee.application.model.bo;

import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
public class OrderItemBO {
	private Long orderItemId;

	private Long orderId;

	private String name;

	private Long shopId;
}
