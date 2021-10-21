/*
 *
 *      Copyright (c) 2018-2025, lihaifeng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lihaifeng (li7hai26@gmail.com)
 *
 */

package com.github.jgzl.infra.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jgzl.infra.upms.dataobject.SysDict;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author lihaifeng
 * @since 2017-11-19
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

}
