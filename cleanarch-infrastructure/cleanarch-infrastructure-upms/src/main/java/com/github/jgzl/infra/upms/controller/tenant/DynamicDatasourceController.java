package com.github.jgzl.infra.upms.controller.tenant;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.util.BeanUtilPlus;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.page.PageRequest;
import com.github.jgzl.infra.upms.domain.dto.DynamicDatasourceReq;
import com.github.jgzl.infra.upms.domain.entity.tenant.DynamicDatasource;
import com.github.jgzl.infra.upms.service.DynamicDatasourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源管理
 * @author lihaifeng
 */
@Slf4j
@RestController
@RequestMapping("/databases")
@RequiredArgsConstructor
@Validated
public class DynamicDatasourceController {

    private final DynamicDatasourceService dynamicDatasourceService;

	/**
	 * 分页查询
	 * @param pageRequest
	 * @param database
	 * @return
	 */
    @GetMapping
    public Result<Page<DynamicDatasource>> page(PageRequest pageRequest, String database) {
        final Page<DynamicDatasource> page = dynamicDatasourceService.page(pageRequest.buildPage(),
                Wraps.<DynamicDatasource>lbQ().eq(DynamicDatasource::getDatabase, database));
        return Result.success(page);
    }

	/**
	 * 查询可用
	 * @return
	 */
	@GetMapping("/active")
    public Result<List<DynamicDatasource>> queryActive() {
        return Result.success(this.dynamicDatasourceService.list(Wraps.<DynamicDatasource>lbQ().eq(DynamicDatasource::getLocked, false)));
    }

	/**
	 * Ping
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}/ping")
    public Result<ResponseEntity<Void>> ping(@PathVariable Long id) {
        this.dynamicDatasourceService.ping(id);
        return Result.success();
    }

	/**
	 * 添加数据源
	 * @param req
	 * @return
	 */
	@PostMapping
    public Result<ResponseEntity<Void>> add(@Validated @RequestBody DynamicDatasourceReq req) {
        dynamicDatasourceService.saveOrUpdateDatabase(BeanUtil.toBean(req, DynamicDatasource.class));
        return Result.success();
    }

	/**
	 * 编辑数据源
	 * @param id
	 * @param req
	 * @return
	 */
    @PutMapping("/{id}")
    public Result<ResponseEntity<Void>> edit(@PathVariable Long id, @Validated @RequestBody DynamicDatasourceReq req) {
        dynamicDatasourceService.saveOrUpdateDatabase(BeanUtilPlus.toBean(id, req, DynamicDatasource.class));
        return Result.success();
    }

	/**
	 * 删除数据源
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
    public Result<ResponseEntity<Void>> remove(@PathVariable Long id) {
        dynamicDatasourceService.removeDatabaseById(id);
        return Result.success();
    }
}
