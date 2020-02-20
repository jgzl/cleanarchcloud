import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gitee.application.DemoApplication;
import com.gitee.application.mapper.OrderMapper;
import com.gitee.application.model.dao.OrderDO;
import com.gitee.application.service.IOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
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
		OrderDO orderDao = new OrderDO();
		orderDao.setId(1L);
		orderDao.setCode("12346789");
		orderDao.setVersion(0);
		orderService.updateById(orderDao);
	}
}
