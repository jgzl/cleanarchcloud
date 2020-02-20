/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gitee.common.data.mybatis;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * @author lihaifeng
 */
public class BaseDOMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		//this.setFieldValByName("createUser", "admin1", metaObject);//版本号3.0.6以及之前的版本
		//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
		this.setInsertFieldValByName("createUser", 0L, metaObject);
		this.setInsertFieldValByName("createDate", LocalDateTime.now(), metaObject);
		this.setInsertFieldValByName("updateUser", 0L, metaObject);
		this.setInsertFieldValByName("updateDate", LocalDateTime.now(), metaObject);
		this.setInsertFieldValByName("version", 0, metaObject);
		this.setInsertFieldValByName("deleted", 0, metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
		this.setInsertFieldValByName("updateUser", 0L, metaObject);
		this.setInsertFieldValByName("updateDate", LocalDateTime.now(), metaObject);
	}
}