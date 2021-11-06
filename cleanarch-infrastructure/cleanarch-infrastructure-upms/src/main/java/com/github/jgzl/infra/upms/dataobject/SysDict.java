package com.github.jgzl.infra.upms.dataobject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.jgzl.common.api.dataobject.BaseDo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典表
 *
 * @author lihaifeng
 * @date 2020/03/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDict extends BaseDo<SysDict> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer id;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 是否是系统内置
	 */
	@TableField(value = "`system`")
	private String system;

	/**
	 * 备注信息
	 */
	private String remarks;

}
