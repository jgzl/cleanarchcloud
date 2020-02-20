package com.gitee.application.model.dao;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.common.data.bo.BaseDO;

import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
@TableName("o_order")
public class OrderDO extends BaseDO implements Serializable {
	@TableId(type = IdType.ID_WORKER)
	private Long id;

	private Long shopId;

	private String code;

	private Date createTime;
}
