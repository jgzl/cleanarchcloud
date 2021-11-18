package com.github.jgzl.common.data.mybatis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lihaifeng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseDictionary {

    private String code;

    private String desc;

}
