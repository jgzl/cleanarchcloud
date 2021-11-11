package com.github.jgzl.infra.upms.domain.dto;

import com.github.jgzl.common.data.page.PageRequest;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 岗位
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
public class StationPageDTO extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Length(max = 255, message = "名称长度不能超过255")
    private String name;
    /**
     * 组织ID
     * #c_core_org
     */
    private Long orgId;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 状态
	 */
	private Boolean status;

}
