package com.github.jgzl.application.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.jgzl.application.SsoClient1Application;
import com.github.jgzl.application.entity.TOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = SsoClient1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TOrderMapperTest {
	@Autowired
	private TOrderMapper mapper;

	@Test
	public void testInsertSuccess() {
		mapper.delete(new QueryWrapper<>());
		for (int i = 0; i < 8000; i++) {
			TOrder entity = new TOrder();
			entity.setOrderDescription("测试订单描述" + i);
			entity.setUserId((long) i % 8);
			mapper.insert(entity);
		}
		List<TOrder> orderEntityList = mapper.selectList(new QueryWrapper<>());
		System.out.println(orderEntityList);
	}
}