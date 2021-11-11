package com.github.jgzl.infra.upms.controller.tenant;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.jgzl.common.core.annotation.log.SysLog;
import com.github.jgzl.common.core.util.BeanUtilPlus;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.security.annotation.Inner;
import com.github.jgzl.infra.upms.domain.dto.TenantConfigDTO;
import com.github.jgzl.infra.upms.domain.dto.TenantPageDTO;
import com.github.jgzl.infra.upms.domain.dto.TenantSaveDTO;
import com.github.jgzl.infra.upms.domain.entity.tenant.Tenant;
import com.github.jgzl.infra.upms.domain.entity.tenant.TenantConfig;
import com.github.jgzl.infra.upms.domain.vo.TenantDynamicDatasourceVO;
import com.github.jgzl.infra.upms.service.DynamicDatasourceService;
import com.github.jgzl.infra.upms.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.jgzl.common.core.util.Result.success;

/**
 * 租户管理
 *
 * @author Levin
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tenants")
public class TenantController {

    private final TenantService tenantService;
    private final DynamicDatasourceService dynamicDatasourceService;

	/**
	 * 租户列表
	 * @param params
	 * @return
	 */
    @GetMapping
    public Result<IPage<Tenant>> query(TenantPageDTO params) {
        return success(tenantService.page(params.buildPage(), Wraps.<Tenant>lbQ()
                .like(Tenant::getName, params.getName()).eq(Tenant::getCode, params.getCode())
                .eq(Tenant::getProvinceId, params.getProvinceId())
                .eq(Tenant::getCityId, params.getCityId())
                .eq(Tenant::getDistrictId, params.getDistrictId())
                .eq(Tenant::getIndustry, params.getIndustry()).eq(Tenant::getStatus, params.getStatus())
                .eq(Tenant::getType, params.getType())));
    }

	/**
	 * 查询可用数据源
	 * @return
	 */
    @Inner
    @GetMapping("/databases/active")
    public Result<List<TenantDynamicDatasourceVO>> queryActive() {
        return success(this.dynamicDatasourceService.selectTenantDynamicDatasource());
    }

	/**
	 * 添加租户
	 * @param dto
	 * @return
	 */
    @PostMapping
    @SysLog(value = "添加租户")
    public Result<ResponseEntity<Void>> add(@Validated @RequestBody TenantSaveDTO dto) {
        tenantService.saveOrUpdateTenant(BeanUtil.toBean(dto, Tenant.class));
        return success();
    }

	/**
	 * 编辑租户
	 * @param id
	 * @param dto
	 * @return
	 */
    @PutMapping("/{id}")
    @SysLog(value = "编辑租户")
    public Result<ResponseEntity<Void>> edit(@PathVariable Long id, @Validated @RequestBody TenantSaveDTO dto) {
        tenantService.saveOrUpdateTenant(BeanUtilPlus.toBean(id, dto, Tenant.class));
        return success();
    }

	/**
	 * 配置租户
	 * @param id
	 * @param dto
	 * @return
	 */
    @PutMapping("/{id}/config")
    @SysLog(value = "配置租户")
    public Result<ResponseEntity<Void>> config(@PathVariable Long id, @Validated @RequestBody TenantConfigDTO dto) {
        tenantService.tenantConfig(TenantConfig.builder().tenantId(id).dynamicDatasourceId(dto.getDynamicDatasourceId()).build());
        return success();
    }

	/**
	 * 加载初始数据
	 * @param id
	 * @return
	 */
    @PutMapping("/{id}/init_sql_script")
    @SysLog(value = "加载初始数据")
    public Result<ResponseEntity<Void>> initSqlScript(@PathVariable Long id) {
        tenantService.initSqlScript(id);
        return success();
    }

	/**
	 * 删除租户
	 * @param id
	 * @return
	 */
    @DeleteMapping("/{id}")
    @SysLog(value = "删除租户")
    public Result<ResponseEntity<Void>> del(@PathVariable Long id) {
        tenantService.removeById(id);
        return success();
    }

}
