
package com.github.jgzl.infra.upms.dataobject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.jgzl.common.api.dataobject.BaseDo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典项
 *
 * @author lihaifeng
 * @date 2020/03/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictItem extends BaseDo<SysDictItem> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer id;

	/**
	 * 所属字典类id
	 */
	private Integer dictId;

	/**
	 * 数据值
	 */
	private String value;

	/**
	 * 标签名
	 */
	private String label;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 排序（升序）
	 */
	private Integer sort;

}
