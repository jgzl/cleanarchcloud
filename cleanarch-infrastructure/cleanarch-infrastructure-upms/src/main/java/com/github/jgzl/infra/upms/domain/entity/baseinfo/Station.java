package com.github.jgzl.infra.upms.domain.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jgzl.common.core.model.SuperEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 岗位
 * </p>
 *
 * @author Levin
 * @since 2019-10-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_station")
public class Station extends SuperEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @NotEmpty(message = "名称不能为空")
    @Length(max = 255, message = "名称长度不能超过255")
    @TableField(value = "`name`", condition = LIKE)
    private String name;

	/**
	 * 岗位编码
	 */
	private String code;

	/**
	 * 岗位类型
	 */
	private Integer type;

	/**
	 * 排序
	 */
	@TableField(value = "`sequence`")
    private Integer sequence;
    /**
     * 组织ID
     * #c_core_org
     */
	@TableField("org_id")
    private Long orgId;


    /**
     * 状态
     */
	@TableField("`status`")
    private Boolean status;

    /**
     * 描述
     */
    @Length(max = 255, message = "描述长度不能超过255")
    private String description;


}
