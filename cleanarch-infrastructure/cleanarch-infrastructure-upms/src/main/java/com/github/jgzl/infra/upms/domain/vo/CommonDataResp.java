package com.github.jgzl.infra.upms.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lihaifeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonDataResp {
    private Long id;
    private String name;
}
