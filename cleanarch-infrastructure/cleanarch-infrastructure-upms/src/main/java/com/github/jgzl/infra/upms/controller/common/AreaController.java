package com.github.jgzl.infra.upms.controller.common;


import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.infra.upms.domain.dto.AreaEntityDTO;
import com.github.jgzl.infra.upms.domain.entity.common.AreaEntity;
import com.github.jgzl.infra.upms.domain.vo.AreaNodeResp;
import com.github.jgzl.infra.upms.service.AreaService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.jgzl.infra.upms.domain.converts.AreaConverts.AREA_DTO_2_PO_CONVERTS;
import static com.github.jgzl.infra.upms.domain.converts.AreaConverts.AREA_ENTITY_2_NODE_RESP_CONVERTS;

/**
 * <p>
 * 地区管理
 * </p>
 *
 * @author lihaifeng
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    /**
     * 查询系统所有的组织树
     */
    @GetMapping("/trees")
    public Result<List<Tree<Long>>> tree() {
        List<AreaEntity> list = this.areaService.list(Wraps.<AreaEntity>lbQ().orderByAsc(AreaEntity::getSequence));
        final List<TreeNode<Long>> nodes = list.stream().map(area -> {
            TreeNode<Long> treeNode = new TreeNode<>(area.getId(), area.getParentId(), area.getName(), area.getSequence());
            Map<String, Object> extra = Maps.newLinkedHashMap();
            extra.put("value", area.getId());
            extra.put("label", area.getName());
            extra.put("level", area.getLevel());
            extra.put("longitude", area.getLongitude());
            extra.put("latitude", area.getLatitude());
            treeNode.setExtra(extra);
            return treeNode;
        }).collect(Collectors.toList());
        return Result.success(TreeUtil.build(nodes, 0L));
    }

    @GetMapping("/{parent_id}/children")
    public Result<List<AreaNodeResp>> list(@PathVariable(name = "parent_id") Integer parentId) {
        final List<AreaEntity> list = this.areaService.listArea(parentId);
        return Result.success(AREA_ENTITY_2_NODE_RESP_CONVERTS.converts(list));
    }

    @PostMapping
    public Result<ResponseEntity<Void>> save(@Validated @RequestBody AreaEntityDTO dto) {
        this.areaService.saveOrUpdateArea(AREA_DTO_2_PO_CONVERTS.convert(dto));
        return Result.success();
    }

    @DeleteMapping
    public Result<ResponseEntity<Void>> batchDel(@RequestBody List<Long> ids) {
        this.areaService.removeByIds(ids);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<ResponseEntity<Void>> del(@PathVariable Integer id) {
        this.areaService.removeById(id);
        return Result.success();
    }
}
