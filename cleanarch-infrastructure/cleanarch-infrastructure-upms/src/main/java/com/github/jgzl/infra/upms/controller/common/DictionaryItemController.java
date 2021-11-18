package com.github.jgzl.infra.upms.controller.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.page.PageRequest;
import com.github.jgzl.infra.upms.domain.dto.DictionaryItemDTO;
import com.github.jgzl.infra.upms.domain.entity.common.DictionaryItem;
import com.github.jgzl.infra.upms.service.DictionaryItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.github.jgzl.infra.upms.domain.converts.DictionaryConverts.DICTIONARY_ITEM_DTO_2_ITEM_PO_CONVERTS;

/**
 * 字典项
 *
 * @author lihaifeng
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/dictionaries/{dictionary_id}/items")
public class DictionaryItemController {

    private final DictionaryItemService dictionaryItemService;

	/**
	 * 查询字典子项
	 * @param dictionaryId 字典ID
	 * @param label  名称
	 * @param status
	 * @param params
	 * @return
	 */
    @GetMapping
    public Result<Page<DictionaryItem>> query(@PathVariable("dictionary_id") Long dictionaryId, String label, Boolean status, PageRequest params) {
        final Page<DictionaryItem> itemPage = this.dictionaryItemService.page(params.buildPage(), Wraps.<DictionaryItem>lbQ()
                .like(DictionaryItem::getLabel, label).eq(DictionaryItem::getStatus, status)
                .eq(DictionaryItem::getDictionaryId, dictionaryId));
        return Result.success(itemPage);
    }

	/**
	 * 添加字典子项
	 * @param dictionaryId 字典ID
	 * @param dto
	 * @return
	 */
    @PostMapping
    public Result<ResponseEntity<Void>> save(@PathVariable("dictionary_id") Long dictionaryId, @Validated @RequestBody DictionaryItemDTO dto) {
        this.dictionaryItemService.addDictionaryItem(dictionaryId, DICTIONARY_ITEM_DTO_2_ITEM_PO_CONVERTS.convert(dto));
        return Result.success();
    }

	/**
	 * 编辑字典子项
	 * @param dictionaryId
	 * @param id
	 * @param dto
	 * @return
	 */
    @PutMapping("/{id}")
    public Result<ResponseEntity<Void>> edit(@PathVariable("dictionary_id") Long dictionaryId, @PathVariable Long id, @Validated @RequestBody DictionaryItemDTO dto) {
        final DictionaryItem dictionaryItem = DICTIONARY_ITEM_DTO_2_ITEM_PO_CONVERTS.convert(dto);
        dictionaryItem.setId(id);
        this.dictionaryItemService.editDictionaryItem(dictionaryId, dictionaryItem);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<ResponseEntity<Void>> del(@PathVariable Long id) {
        this.dictionaryItemService.removeById(id);
        return Result.success();
    }

}
