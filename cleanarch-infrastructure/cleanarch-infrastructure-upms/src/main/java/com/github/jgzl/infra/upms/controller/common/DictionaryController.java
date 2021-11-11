package com.github.jgzl.infra.upms.controller.common;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.annotation.log.SysLog;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.page.PageRequest;
import com.github.jgzl.infra.upms.domain.dto.DictionaryDTO;
import com.github.jgzl.infra.upms.domain.entity.common.Dictionary;
import com.github.jgzl.infra.upms.domain.entity.common.DictionaryItem;
import com.github.jgzl.infra.upms.service.DictionaryItemService;
import com.github.jgzl.infra.upms.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.jgzl.infra.upms.domain.converts.DictionaryConverts.DICTIONARY_DTO_2_PO_CONVERTS;

/**
 * 字典类型
 *
 * @author Levin
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/dictionaries")
@RequiredArgsConstructor
public class DictionaryController {


    private final DictionaryService dictionaryService;
    private final DictionaryItemService dictionaryItemService;

    @GetMapping
    @SysLog(value = "字典查询")
    public Result<IPage<Dictionary>> query(PageRequest pageRequest, String name, String code, Boolean status) {
        final Page<Dictionary> page = this.dictionaryService.page(pageRequest.buildPage(),
                Wraps.<Dictionary>lbQ().eq(Dictionary::getStatus, status)
                        .like(Dictionary::getCode, code).like(Dictionary::getName, name));
        return Result.success(page);
    }

    @PostMapping
    @SysLog(value = "字典新增")
    public Result<ResponseEntity<Void>> save(@Validated @RequestBody DictionaryDTO dto) {
        this.dictionaryService.addDictionary(DICTIONARY_DTO_2_PO_CONVERTS.convert(dto));
        return Result.success();
    }

    @PutMapping("/{id}")
    @SysLog(value = "字典编辑")
    public Result<ResponseEntity<Void>> edit(@PathVariable Long id, @Validated @RequestBody DictionaryDTO dto) {
        this.dictionaryService.editDictionary(DICTIONARY_DTO_2_PO_CONVERTS.convert(dto, id));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @SysLog(value = "删除指定字典项", request = true)
    public Result<ResponseEntity<Void>> del(@PathVariable Long id) {
        this.dictionaryService.deleteById(id);
        return Result.success();
    }


    @GetMapping("/{dictionary_code}/list")
    public Result<List<DictionaryItem>> list(@PathVariable("dictionary_code") String dictionaryCode) {
        final List<DictionaryItem> list = this.dictionaryItemService.list(Wraps.<DictionaryItem>lbQ()
                .eq(DictionaryItem::getStatus, true).eq(DictionaryItem::getDictionaryCode, dictionaryCode));
        return Result.success(list);
    }
}
