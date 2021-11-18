package com.github.jgzl.infra.upms.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lihaifeng
 */
@Data
public class TenantConfigDTO {

    @NotNull(message = "动态数据源ID不能为空")
    private Long dynamicDatasourceId;

}
