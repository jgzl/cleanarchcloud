
package com.github.jgzl.infra.upms.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.github.jgzl.common.data.bo.BaseDo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典项
 *
 * @author lihaifeng
 * @date 2019/03/19
 */
@Data
@ApiModel(value = "字典项")
@EqualsAndHashCode(callSuper = true)
public class SysDictItem extends BaseDo<SysDictItem> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	@ApiModelProperty(value = "字典项id")
	private Integer id;

	/**
	 * 所属字典类id
	 */
	@ApiModelProperty(value = "所属字典类id")
	private Integer dictId;

	/**
	 * 数据值
	 */
	@ApiModelProperty(value = "数据值")
	private String value;

	/**
	 * 标签名
	 */
	@ApiModelProperty(value = "标签名")
	private String label;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private String type;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 排序（升序）
	 */
	@ApiModelProperty(value = "排序值，默认升序")
	private Integer sort;

}
