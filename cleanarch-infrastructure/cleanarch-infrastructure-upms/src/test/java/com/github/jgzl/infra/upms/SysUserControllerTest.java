package com.github.jgzl.infra.upms;

import cn.hutool.core.lang.Assert;
import com.github.jgzl.common.api.vo.SysUserVo;
import com.github.jgzl.infra.upms.service.SysUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
public class SysUserControllerTest {

	@Autowired
	private SysUserService userService;

	@Test
	public void list() {
		userService.list().forEach(System.out::println);
	}

	@Test
	@SneakyThrows
	public void save() {
		SysUserVo user = new SysUserVo();
		user.setUserId("-10");
		user.setUsername("test");
		user.setPassword("test");
		user.setMobile("17621006318");
		user.setEmail("test@test.com");
		user.setNickname("test-nickname");
		userService.add(user);
		when(userService.add(user)).thenReturn(true);
	}

	@Test
	public void delete() {
		boolean b = userService.removeById(-1L);
		Assert.isFalse(!b);
	}
}
