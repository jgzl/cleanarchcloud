package com.github.jgzl.infra.upms.domain.dto;

import com.github.jgzl.common.data.mybatis.auth.DataScopeType;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 实体类
 * 角色
 * </p>
 *
 * @author Levin
 * @since 2019-11-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名称不能为空")
    @Length(max = 30, message = "角色名称长度不能超过30")
    private String name;
    /**
     * 角色编码
     */
    @Length(max = 20, message = "角色编码长度不能超过20")
    private String code;
    /**
     * 描述
     */
    @Length(max = 100, message = "描述长度不能超过100")
    private String description;
    /**
     * 状态
     */
    private Boolean locked;

    private Boolean readonly;
    /**
     * 数据权限类型
     * #DataScopeType{ALL:1,全部;THIS_LEVEL:2,本级;THIS_LEVEL_CHILDREN:3,本级以及子级;CUSTOMIZE:4,自定义;SELF:5,个人;}
     */
    @NotNull(message = "数据权限类型不能为空")
    private DataScopeType scopeType;

    /**
     * 关联的组织id
     */
    private List<Long> orgList;
}
