package com.github.jgzl.infra.upms.service.impl;

import com.github.jgzl.common.core.exception.CheckedException;
import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.infra.upms.domain.entity.common.Dictionary;
import com.github.jgzl.infra.upms.domain.entity.common.DictionaryItem;
import com.github.jgzl.infra.upms.repository.DictionaryItemMapper;
import com.github.jgzl.infra.upms.repository.DictionaryMapper;
import com.github.jgzl.infra.upms.service.DictionaryItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 业务实现类
 * 字典项
 * </p>
 *
 * @author zuihou
 * @date 2019-07-02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryItemServiceImpl extends SuperServiceImpl<DictionaryItemMapper, DictionaryItem> implements DictionaryItemService {

    private final DictionaryMapper dictionaryMapper;


    @Override
    public void addDictionaryItem(Long dictionaryId, DictionaryItem item) {
        final long count = this.baseMapper.selectCount(Wraps.<DictionaryItem>lbQ()
                .eq(DictionaryItem::getValue, item.getValue())
                .eq(DictionaryItem::getDictionaryId, dictionaryId));
        if (count > 0) {
            throw CheckedException.badRequest("编码已存在");
        }
        final Dictionary dictionary = Optional.ofNullable(this.dictionaryMapper.selectById(dictionaryId))
                .orElseThrow(() -> CheckedException.notFound("码表不存在"));
        item.setDictionaryId(dictionaryId);
        item.setDictionaryCode(dictionary.getCode());
        this.baseMapper.insert(item);
    }

    @Override
    public void editDictionaryItem(Long dictionaryId, DictionaryItem item) {
        final long count = this.baseMapper.selectCount(Wraps.<DictionaryItem>lbQ()
                .eq(DictionaryItem::getValue, item.getValue())
                .ne(DictionaryItem::getId, item.getId())
                .eq(DictionaryItem::getDictionaryId, dictionaryId));
        if (count > 0) {
            throw CheckedException.badRequest("编码已存在");
        }
        this.baseMapper.updateById(item);
    }
}
