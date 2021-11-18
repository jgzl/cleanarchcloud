package com.github.jgzl.infra.upms.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author lihaifeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaNodeResp {

    private Long value;
    private String label;
    private Integer level;
    protected Long parentId;
    private Boolean isLeaf;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 纬度
     */
    private BigDecimal latitude;

}
