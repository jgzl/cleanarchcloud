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

package com.github.jgzl.common.data.mybatis;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * @author lihaifeng
 */
public class BaseDOMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, "createUser", Long.class, 0L);
		this.strictInsertFill(metaObject, "createDate", LocalDateTime.class, LocalDateTime.now());
		this.strictUpdateFill(metaObject, "updateUser", Long.class, 0L);
		this.strictUpdateFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "version", Integer.class, 0);
		this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictUpdateFill(metaObject, "updateUser", Long.class, 0L);
		this.strictUpdateFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
	}
}