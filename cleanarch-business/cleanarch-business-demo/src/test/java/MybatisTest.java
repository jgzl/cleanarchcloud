import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.jgzl.application.DemoApplication;
import com.github.jgzl.application.mapper.OrderMapper;
import com.github.jgzl.application.model.dao.OrderDo;
import com.github.jgzl.application.service.IOrderService;


@SpringBootTest(classes = {DemoApplication.class})
public class MybatisTest {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private OrderMapper orderMapper;

	@Test
	public void test1() {
		orderService.pageList();
	}

	@Test
	public void test2() {
		List<Map<Object, Object>> list = orderMapper.list();
		list.forEach(m -> System.out.println(m));
	}

	@Test
	public void insert() {
		orderService.insert();
	}

	@Test
	public void update() {
		OrderDo orderDao = new OrderDo();
		orderDao.setId(1L);
		orderDao.setCode("12346789");
		orderDao.setVersion(0);
		orderService.updateById(orderDao);
	}
}
