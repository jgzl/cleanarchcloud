package com.github.jgzl.infra.upms.dataobject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jgzl.common.api.dataobject.BaseDo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 路由
 *
 * @author lihaifeng
 * @date 2018-11-06 10:17:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRouteConf extends BaseDo<SysRouteConf> {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 路由ID
	 */
	private String routeId;

	/**
	 * 路由名称
	 */
	private String routeName;

	/**
	 * 断言
	 */
	private String predicates;

	/**
	 * 过滤器
	 */
	private String filters;

	/**
	 * uri
	 */
	private String uri;

	/**
	 * 排序
	 */
	@TableField(value = "`order`")
	private Integer order;

}
