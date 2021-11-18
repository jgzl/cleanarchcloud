package com.github.jgzl.infra.upms.domain.dto;

import com.github.jgzl.common.core.model.SuperEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 用户
 * </p>
 *
 * @author lihaifeng
 * @since 2019-11-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class UserUpdateImageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 头像
     */
    @Length(max = 255, message = "头像长度不能超过255")
    private String image;

}
