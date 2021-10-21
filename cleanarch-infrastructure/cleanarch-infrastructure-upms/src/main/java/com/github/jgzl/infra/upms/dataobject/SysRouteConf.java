package com.github.jgzl.infra.upms.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jgzl.common.data.bo.BaseDo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 路由
 *
 * @author lihaifeng
 * @date 2018-11-06 10:17:18
 */
@Data
@ApiModel(value = "网关路由信息")
@EqualsAndHashCode(callSuper = true)
public class SysRouteConf extends BaseDo<SysRouteConf> {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "主键")
	private Integer id;

	/**
	 * 路由ID
	 */
	@ApiModelProperty(value = "路由id")
	private String routeId;

	/**
	 * 路由名称
	 */
	@ApiModelProperty(value = "路由名称")
	private String routeName;

	/**
	 * 断言
	 */
	@ApiModelProperty(value = "断言")
	private String predicates;

	/**
	 * 过滤器
	 */
	@ApiModelProperty(value = "过滤器")
	private String filters;

	/**
	 * uri
	 */
	@ApiModelProperty(value = "请求uri")
	private String uri;

	/**
	 * 排序
	 */
	@TableField(value = "`order`")
	@ApiModelProperty(value = "排序值")
	private Integer order;

}
