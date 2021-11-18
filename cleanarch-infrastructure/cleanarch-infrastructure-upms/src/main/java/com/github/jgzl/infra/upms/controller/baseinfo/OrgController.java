package com.github.jgzl.infra.upms.controller.baseinfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.github.jgzl.common.core.annotation.log.SysLog;
import com.github.jgzl.common.core.util.BeanUtilPlus;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.infra.upms.domain.dto.OrgSaveDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Org;
import com.github.jgzl.infra.upms.service.OrgService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.jgzl.common.core.util.Result.success;


/**
 * 组织架构
 *
 * @author lihaifeng
 */
@Slf4j
@RestController
@RequestMapping("/org")
@RequiredArgsConstructor
public class OrgController {

    private final OrgService orgService;

    /**
     * 查询系统所有的组织树
     */
    @GetMapping("/trees")
    public Result<List<Tree<Long>>> tree(String name, Boolean status) {
        List<Org> list = this.orgService.list(Wraps.<Org>lbQ().like(Org::getLabel, name).eq(Org::getStatus, status).orderByAsc(Org::getSequence));
        final List<TreeNode<Long>> nodes = list.stream().map(org -> {
            TreeNode<Long> treeNode = new TreeNode<>(org.getId(), org.getParentId(), org.getLabel(), org.getSequence());
            Map<String, Object> extra = Maps.newLinkedHashMap();
            extra.put("label", org.getLabel());
            extra.put("alias", org.getAlias());
            extra.put("status", org.getStatus());
            extra.put("sequence", org.getSequence());
            extra.put("tel", org.getTel());
            extra.put("description", org.getDescription());
            treeNode.setExtra(extra);
            return treeNode;
        }).collect(Collectors.toList());
        return success(TreeUtil.build(nodes, 0L));
    }

    @PostMapping
    @SysLog(value = "保存组织架构")
    public Result<ResponseEntity<Void>> save(@Validated @RequestBody OrgSaveDTO dto) {
        orgService.addOrg(BeanUtil.toBean(dto, Org.class));
        return success();
    }

    @PutMapping("/{id}")
    @SysLog(value = "编辑组织架构")
    public Result<ResponseEntity<Void>> edit(@PathVariable Long id, @Validated @RequestBody OrgSaveDTO dto) {
        orgService.updateById(BeanUtilPlus.toBean(id, dto, Org.class));
        return success();
    }

    @DeleteMapping("/{id}")
    @SysLog(value = "删除组织架构")
    public Result<ResponseEntity<Void>> del(@PathVariable Long id) {
        orgService.remove(id);
        return success();
    }


}
