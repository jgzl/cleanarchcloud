package com.github.jgzl.infra.upms.domain.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jgzl.common.core.model.TreeEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 组织
 * </p>
 *
 * @author lihaifeng
 * @since 2019-10-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_org")
public class Org extends TreeEntity<Org, Long> {

    private static final long serialVersionUID = 1L;

    protected String label;

    private Long tenantId;

    /**
     * 简称
     */
    @Length(max = 255, message = "简称长度不能超过255")
    @TableField(value = "alias", condition = LIKE)
    private String alias;

    @TableField("`tree_path`")
    private String treePath;

    /**
     * 状态
     */
    @TableField("status")
    private Boolean status;

    /**
     * 描述
     */
    @Length(max = 255, message = "描述长度不能超过255")
    @TableField(value = "description", condition = LIKE)
    private String description;

}
