package com.github.jgzl.infra.upms.domain.entity.tenant;


import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jgzl.common.core.model.SuperEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 租户配置信息
 * </p>
 *
 * @author lihaifeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_tenant_config")
public class TenantConfig extends SuperEntity<Long> {

    private Long tenantId;
    private Long dynamicDatasourceId;

}
