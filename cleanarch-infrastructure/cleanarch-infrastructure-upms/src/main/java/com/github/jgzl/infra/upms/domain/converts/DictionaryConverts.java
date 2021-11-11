package com.github.jgzl.infra.upms.domain.converts;

import com.github.jgzl.common.data.page.BasePageConverts;
import com.github.jgzl.infra.upms.domain.dto.DictionaryDTO;
import com.github.jgzl.infra.upms.domain.dto.DictionaryItemDTO;
import com.github.jgzl.infra.upms.domain.entity.common.Dictionary;
import com.github.jgzl.infra.upms.domain.entity.common.DictionaryItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * @author Levin
 */
@Slf4j
public class DictionaryConverts {

    public static final DictionaryDto2PoConverts DICTIONARY_DTO_2_PO_CONVERTS = new DictionaryDto2PoConverts();

    public static class DictionaryDto2PoConverts implements BasePageConverts<DictionaryDTO, Dictionary> {

        @Override
        public Dictionary convert(DictionaryDTO source, Long id) {
            if (source == null) {
                return null;
            }
            Dictionary target = new Dictionary();
            BeanUtils.copyProperties(source, target);
            target.setId(id);
            return target;
        }
    }

    public static final DictionaryItemDto2ItemPoConverts DICTIONARY_ITEM_DTO_2_ITEM_PO_CONVERTS = new DictionaryItemDto2ItemPoConverts();

    public static class DictionaryItemDto2ItemPoConverts implements BasePageConverts<DictionaryItemDTO, DictionaryItem> {

        @Override
        public DictionaryItem convert(DictionaryItemDTO source) {
            if (source == null) {
                return null;
            }
            DictionaryItem target = new DictionaryItem();
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }

}
