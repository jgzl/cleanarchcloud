package com.github.jgzl.infra.upms.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Vue路由 Meta
 *
 * @author Levin
 * @since 2019-10-20 15:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterMeta implements Serializable {

    private static final long serialVersionUID = 5499925008927195914L;

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 面包屑
	 */
	private Boolean breadcrumb = true;

}
