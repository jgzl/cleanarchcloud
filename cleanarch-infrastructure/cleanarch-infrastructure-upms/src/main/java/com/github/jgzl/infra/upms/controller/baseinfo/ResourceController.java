package com.github.jgzl.infra.upms.controller.baseinfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.annotation.log.SysLog;
import com.github.jgzl.common.core.util.BeanUtilPlus;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.TenantEnvironment;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.mybatis.conditions.query.LbqWrapper;
import com.github.jgzl.infra.upms.domain.dto.ResourceQueryDTO;
import com.github.jgzl.infra.upms.domain.dto.ResourceSaveDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Resource;
import com.github.jgzl.infra.upms.domain.enums.ResourceType;
import com.github.jgzl.infra.upms.domain.vo.VueRouter;
import com.github.jgzl.infra.upms.service.ResourceService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.github.jgzl.infra.upms.domain.converts.MenuConverts.VUE_ROUTER_2_TREE_NODE_CONVERTS;
import static java.util.stream.Collectors.toList;

/**
 * 菜单资源
 *
 * @author lihaifeng
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;
    private final TenantEnvironment tenantEnvironment;

	/**
	 * 菜单路由
	 * @param all
	 * @return
	 */
    @GetMapping("/router")
    public Result<List<Tree<Long>>> router(@RequestParam(required = false, defaultValue = "false") Boolean all) {
        List<VueRouter> routers = resourceService.findVisibleResource(ResourceQueryDTO.builder().userId(tenantEnvironment.userId()).build());
        List<TreeNode<Long>> list = routers.stream()
                .filter(router -> all || (router.getType() != null && router.getType() == 1 || router.getType() == 5))
                .map(VUE_ROUTER_2_TREE_NODE_CONVERTS::convert).collect(toList());
        return Result.success(TreeUtil.build(list, 0L));
    }

	/**
	 * 资源码
	 * @return
	 */
    @GetMapping("/permissions")
    public Result<List<String>> permissions() {
        List<VueRouter> routers = Optional.ofNullable(resourceService.findVisibleResource(ResourceQueryDTO.builder()
                .userId(tenantEnvironment.userId()).build())).orElseGet(Lists::newArrayList);
        return Result.success(routers.stream().map(VueRouter::getPermission).collect(toList()));
    }

	/**
	 * 资源列表
	 * @param current
	 * @param size
	 * @param parentId 父ID
	 * @param type 资源类型
	 * @return
	 */
    @GetMapping
    public Result<IPage<Resource>> query(@RequestParam(required = false, defaultValue = "1") Integer current,
										 @RequestParam(required = false, defaultValue = "20") Integer size,
										 Long parentId, Integer type) {
        final LbqWrapper<Resource> wrapper = Wraps.<Resource>lbQ().eq(Resource::getParentId, parentId).eq(Resource::getType, ResourceType.BUTTON);
        IPage<Resource> page = resourceService.page(new Page<>(current, size), wrapper);
        return Result.success(page);
    }

	/**
	 * 添加资源
	 * @param data
	 * @return
	 */
    @PostMapping
    @SysLog(value = "添加资源")
    public Result<ResponseEntity<Void>> save(@Validated @RequestBody ResourceSaveDTO data) {
        Resource resource = BeanUtil.toBean(data, Resource.class);
        resourceService.addResource(resource);
        return Result.success();
    }

	/**
	 * 删除资源
	 * @param id
	 * @return
	 */
    @DeleteMapping("/{id}")
    @SysLog(value = "删除资源")
    public Result<ResponseEntity<Void>> del(@PathVariable Long id) {
        this.resourceService.delResource(id);
        return Result.success();
    }

	/**
	 * 修改资源
	 * @param id
	 * @param data
	 * @return
	 */
    @PutMapping("/{id}")
    @SysLog(value = "修改资源")
    public Result<ResponseEntity<Void>> edit(@PathVariable Long id, @Validated @RequestBody ResourceSaveDTO data) {
        resourceService.editResourceById(BeanUtilPlus.toBean(id, data, Resource.class));
        return Result.success();
    }


}
