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

package com.gitee.common.upms.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gitee.common.core.constant.ServiceNameConstants;
import com.gitee.common.core.util.Result;
import com.gitee.common.upms.dao.PlatformSsoUserDO;
import com.gitee.common.upms.dto.UserInfoDTO;

/**
 * @author lihaifeng
 * @date 2018/6/22
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteUserService {
	/**
	 * 通过用户名查询用户、角色信息
	 *
	 * @param username 用户名
	 * @return Result
	 */
	@GetMapping("/user/info/{username}")
	Result<UserInfoDTO> info(@PathVariable("username") String username);

	/**
	 * 通过社交账号或手机号查询用户、角色信息
	 *
	 * @param login appid@code
	 * @return
	 */
	@GetMapping("/social/info/{login}")
	Result<UserInfoDTO> social(@PathVariable("login") String login);

	/**
	 * 查询上级部门的用户信息
	 *
	 * @param username 用户名
	 * @return Result
	 */
	@GetMapping("/user/ancestor/{username}")
	Result<List<PlatformSsoUserDO>> ancestorUsers(@PathVariable("username") String username);
}
