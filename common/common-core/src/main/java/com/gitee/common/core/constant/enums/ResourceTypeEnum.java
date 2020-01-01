/*
 *    Copyright (c) 2018-2025, lihaifeng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the dlihaifeng.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lihaifeng (wangiegie@gmail.com)
 */

package com.gitee.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lihaifeng
 * @date 2018/9/30
 * 资源类型
 */
@Getter
@AllArgsConstructor
public enum ResourceTypeEnum {
  /**
   * 图片资源
   */
  IMAGE("image", "图片资源"),

  /**
   * xml资源
   */
  XML("xml", "xml资源");

  /**
   * 类型
   */
  private final String type;

  /**
   * 描述
   */
  private final String description;
}
