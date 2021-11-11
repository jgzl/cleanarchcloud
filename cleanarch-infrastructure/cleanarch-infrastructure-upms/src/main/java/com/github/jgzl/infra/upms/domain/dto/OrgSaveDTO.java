package com.github.jgzl.infra.upms.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 组织
 * </p>
 *
 * @author Levin
 * @since 2019-07-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrgSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    @Length(max = 255, message = "名称长度不能超过255")
    private String label;
    /**
     * 简称
     */
    @Length(max = 255, message = "简称长度不能超过255")
    private String abbreviation;

    private String tel;
    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sequence;
    /**
     * 状态
     */
    private Boolean status;
    /**
     * 描述
     */
    @Length(max = 255, message = "描述长度不能超过255")
    private String description;

}
