package com.github.jgzl.infra.upms.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源 查询DTO
 *
 * @author lihaifeng
 * @since 2019/06/05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceQueryDTO {

	/**
	 * 资源类型
	 */
	private Integer type;
    /**
     * 父资源id， 用于查询按钮
     */
    private Long parentId;
    /**
     * 登录人用户id
     */
    private Long userId;

}
