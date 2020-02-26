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
 *//*


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gitee.application.auth.AuthApplication;
import com.gitee.application.auth.service.SsoUserService;
import com.gitee.common.upms.dto.UserDTO;

import cn.hutool.core.lang.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AuthApplication.class})
public class SsoUserTest {

	@Autowired
	private SsoUserService userService;

	@Test
	public void list() {
		userService.list().forEach(System.out::println);
	}

	@Test
	public void save() {
		UserDTO user = new UserDTO();
		user.setId(-1L);
		user.setUsername("test");
		user.setPassword("test");
		user.setMobile("17621006318");
		user.setEmail("test@test.com");
		user.setNickname("test-nickname");
		userService.saveUser(user);
	}

	@Test
	public void delete() {
		boolean b = userService.removeById(-1L);
		Assert.isFalse(!b);
	}
}
*/
