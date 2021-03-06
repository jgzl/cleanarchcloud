package com.github.jgzl.application.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
@Builder
public class WelcomeVo {
	private String userName;

	private String urlName;

	private String urlDescription;
}
