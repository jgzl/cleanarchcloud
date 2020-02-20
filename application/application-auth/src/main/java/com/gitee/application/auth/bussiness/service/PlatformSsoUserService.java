/*
 * Copyright [2020] [lihaifeng,xuhang]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.gitee.application.auth.bussiness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.common.upms.dao.PlatformSsoUserDO;
import com.gitee.common.upms.dto.UserDTO;

/**
 * @author lihaifeng
 */
public interface PlatformSsoUserService extends IService<PlatformSsoUserDO> {
	/**
	 * 创建新用户
	 * @param userDTO
	 */
	void saveUser(UserDTO userDTO);
}
