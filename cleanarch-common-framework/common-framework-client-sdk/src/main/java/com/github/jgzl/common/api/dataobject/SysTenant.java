package com.github.jgzl.common.api.dataobject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 租户
 *
 * @author lihaifeng
 * @date 2019-05-15 15:55:41
 */
@Data
public class SysTenant extends BaseDo<SysTenant> {

	private static final long serialVersionUID = 1L;

	/**
	 * 租户id
	 */
	@TableId
	private Integer id;

	/**
	 * 租户名称
	 */
	private String name;

	/**
	 * 租户编号
	 */
	private String code;

	/**
	 * 开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	private LocalDateTime endTime;

	/**
	 * 0正常 9-冻结
	 */
	private String status;

}
