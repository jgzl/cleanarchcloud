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
package com.gitee.application.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gitee.application.auth.service.SysUserService;
import com.gitee.common.security.vo.SysUserVO;

import cn.hutool.core.lang.Assert;

@SpringBootTest(classes = {AuthApplication.class})
public class SysUserTest {

	@Autowired
	private SysUserService userService;

	@Test
	public void list() {
		userService.list().forEach(System.out::println);
	}

	@Test
	public void save() {
		SysUserVO user = new SysUserVO();
		user.setUserId("-10");
		user.setUsername("test");
		user.setPassword("test");
		user.setMobile("17621006318");
		user.setEmail("test@test.com");
		user.setNickname("test-nickname");
		userService.add(user);
	}

	@Test
	public void delete() {
		boolean b = userService.removeById(-1L);
		Assert.isFalse(!b);
	}
}
